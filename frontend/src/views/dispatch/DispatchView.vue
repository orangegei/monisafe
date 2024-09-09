<script lang="ts" setup>
import { ref, computed } from 'vue';
import FrameView from '../FrameView.vue';

// 分页相关数据
const currentPage = ref(1);
const pageSize = ref(11);

// 所有运维人员数据（包含 user_role 字段）
const maintenanceStaff = ref([
    { id: 101, province: '北京', phone: '13812345678', email: 'beijing101@example.com', status: '在线', user_role: 1 },
    { id: 102, province: '上海', phone: '13712345679', email: 'shanghai102@example.com', status: '离线', user_role: 2 },
    { id: 103, province: '广东', phone: '13912345680', email: 'guangdong103@example.com', status: '在线', user_role: 2 },
    { id: 104, province: '浙江', phone: '13512345681', email: 'zhejiang104@example.com', status: '休息', user_role: 1 },
    { id: 105, province: '湖北', phone: '13612345682', email: 'hubei105@example.com', status: '在线', user_role: 2 },
    { id: 106, province: '江苏', phone: '13812345683', email: 'jiangsu106@example.com', status: '在线', user_role: 1 },
    { id: 107, province: '湖南', phone: '13712345684', email: 'hunan107@example.com', status: '离线', user_role: 1 },
    { id: 108, province: '重庆', phone: '13912345685', email: 'chongqing108@example.com', status: '在线', user_role: 2 },
    { id: 109, province: '四川', phone: '13512345686', email: 'sichuan109@example.com', status: '休息', user_role: 2 },
    { id: 110, province: '福建', phone: '13612345687', email: 'fujian110@example.com', status: '在线', user_role: 1 },
    { id: 111, province: '江西', phone: '13312290686', email: 'jiangxi111@example.com', status: '在线', user_role: 1 },
    { id: 111, province: '江西', phone: '13312290686', email: 'jiangxi111@example.com', status: '在线', user_role: 1 },
    { id: 111, province: '江西', phone: '13312290686', email: 'jiangxi111@example.com', status: '在线', user_role: 1 },
    { id: 111, province: '江西', phone: '13312290686', email: 'jiangxi111@example.com', status: '在线', user_role: 1 },
    // 更多数据...
]);

// 筛选方法
const filterStaff = (role: string | number) => {
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
                            <el-button @click="filterStaff(1)">维修人员</el-button>
                            <el-button @click="filterStaff(2)">业务人员</el-button>
                        </el-button-group>
                    </div>

                    <div class="table-container">
                        <el-table :data="paginatedStaff" border style="width: 100%">
                            <!-- 用户角色列 -->
                            <el-table-column prop="user_role" label="用户角色" width="150px">
                                <template #default="{ row }">
                                    {{ row.user_role === 1 ? '维修人员' : '业务人员' }}
                                </template>
                            </el-table-column>

                            <el-table-column prop="id" label="人员ID" width="150px"></el-table-column>
                            <el-table-column prop="province" label="所属省份" width="150px"></el-table-column>
                            <el-table-column prop="phone" label="手机号" width="200px"></el-table-column>
                            <el-table-column prop="email" label="邮箱" width="250px"></el-table-column>
                            <el-table-column prop="status" label="当前状态" width="auto"></el-table-column>
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
    margin-bottom: 1%;
}
</style>