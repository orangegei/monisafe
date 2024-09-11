<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue';
import FrameView from '../FrameView.vue';
import instance from '@/utils/request';

// 分页相关数据
const currentPage = ref(1);
const pageSize = ref(11);

// 所有人员数据
const maintenanceStaff = ref([]);

// 筛选方法
const filterStaff = (role: string) => {
    if (role === 'all') {
        filteredStaff.value = maintenanceStaff.value;
    } else {
        filteredStaff.value = maintenanceStaff.value.filter(staff => staff.user_role === role);
    }
    currentPage.value = 1; // 重置分页到第一页
};

// 筛选后的数据
const filteredStaff = ref<typeof maintenanceStaff.value>([]);

// 初始化筛选数据
filterStaff('all');

// 计算当前页显示的数据
const paginatedStaff = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return filteredStaff.value.slice(start, end);
});

const fetchStaff = async () => {
    try {
        const response = await instance.get('/dispatch', {
            headers: {
                Authorization: sessionStorage.getItem('token')
            },
        });

        if (response.data.code === 0) {
            // 更新人员数据
            maintenanceStaff.value = response.data.data.map((log: any) => ({
                id: log.userId,
                province: log.province,
                phone: log.phoneNumber,
                email: log.emailAddress,
                status: log.status,
                user_role: log.userRole,
            }));
            console.log(maintenanceStaff);
            filterStaff('all'); // 默认筛选所有人员
        }
    } catch (error) {
        console.error('Failed to fetch logs data', error);
    }
};

// 在组件挂载时调用 fetchStaff 来获取人员数据
onMounted(() => {
    fetchStaff();
});
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="white-space: nowrap;">人员查询</span>
                        <el-button-group>
                            <el-button @click="filterStaff('all')">所有人员</el-button>
                            <el-button @click="filterStaff('运维人员')">运维人员</el-button>
                            <el-button @click="filterStaff('业务人员')">业务人员</el-button>
                            <el-button @click="filterStaff('监控人员')">监控人员</el-button>
                        </el-button-group>
                    </div>

                    <div class="table-container">
                        <el-table :data="paginatedStaff" border style="width: 100%">
                            <!-- 用户角色列 -->
                            <el-table-column prop="user_role" label="用户角色" width="auto" align="center"></el-table-column>
                            <el-table-column prop="id" label="人员ID" width="auto" align="center"></el-table-column>
                            <el-table-column prop="province" label="所属省份" width="auto" align="center"></el-table-column>
                            <el-table-column prop="phone" label="手机号" width="auto" align="center"></el-table-column>
                            <el-table-column prop="email" label="邮箱" width="auto" align="center"></el-table-column>
                            <el-table-column prop="status" label="当前状态" width="auto" align="center"></el-table-column>
                        </el-table>
                    </div>

                    <el-pagination
                        v-if="filteredStaff.length > pageSize"
                        v-model:current-page="currentPage"
                        :page-size="pageSize"
                        :total="filteredStaff.length"
                        layout="prev, pager, next"
                        style="margin-top: 20px; text-align: right;">
                    </el-pagination>
                </el-card>
            </div>
        </template>
    </FrameView>
</template>

<style scoped>
.container {
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.box-card {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

.clearfix {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1vh;
}
</style>
