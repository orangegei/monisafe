<script lang="ts" setup>
import { ref, computed, watch } from 'vue';
import FrameView from '../FrameView.vue';

// 分页相关数据
const currentPage = ref(1);
const pageSize = ref(10);

// 所有运维人员数据（包含 user_role 字段）
const maintenanceStaff = ref([
    { id: 101, province: '北京', phone: '13812345678', email: 'beijing101@example.com', status: '在线' , user_role: 1 },
    { id: 102, province: '上海', phone: '13712345679', email: 'shanghai102@example.com', status: '离线' , user_role: 2 },
    { id: 103, province: '广东', phone: '13912345680', email: 'guangdong103@example.com', status: '在线' , user_role: 2 },
    { id: 104, province: '浙江', phone: '13512345681', email: 'zhejiang104@example.com', status: '休息' , user_role: 1 },
    { id: 105, province: '湖北', phone: '13612345682', email: 'hubei105@example.com', status: '在线' , user_role: 2 },
    { id: 106, province: '江苏', phone: '13812345683', email: 'jiangsu106@example.com', status: '在线' , user_role: 1 },
    { id: 107, province: '湖南', phone: '13712345684', email: 'hunan107@example.com', status: '离线' , user_role: 1 },
    { id: 108, province: '重庆', phone: '13912345685', email: 'chongqing108@example.com', status: '在线' , user_role: 2 },
    { id: 109, province: '四川', phone: '13512345686', email: 'sichuan109@example.com', status: '休息' , user_role: 2 },
    { id: 110, province: '福建', phone: '13612345687', email: 'fujian110@example.com', status: '在线' , user_role: 1 },
    { id: 111, province: '江西', phone: '13312290686', email: 'jiangxi111@example.com', status: '在线' , user_role: 1 },
    // 更多数据...
]);

// 过滤后的角色类型，默认为 null（表示全部）
const filteredRole = ref(null);

// 计算当前页显示的数据，且根据 user_role 过滤
const paginatedStaff = computed(() => {
    const filteredData = filteredRole.value === null 
        ? maintenanceStaff.value 
        : maintenanceStaff.value.filter(item => item.user_role === filteredRole.value);
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return filteredData.slice(start, end);
});

// 计算过滤后的数据总数
const filteredStaffCount = computed(() => {
    return filteredRole.value === null 
        ? maintenanceStaff.value.length 
        : maintenanceStaff.value.filter(item => item.user_role === filteredRole.value).length;
});

// 处理筛选变更的函数
const handleRoleFilter = (value) => {
    filteredRole.value = value; // 更新过滤角色
};

// 监听 filteredRole 的变化
watch(filteredRole, () => {
    currentPage.value = 1; // 重置分页到第一页
});
</script>

<template>
    <FrameView>
        <template #main-body>
            <div class="container">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="white-space: nowrap;">人员查询</span>
                    </div>

                    <div class="table-container">
                        <el-table :data="paginatedStaff" border style="width: 100%">
                            <!-- 用户角色筛选列 -->
                            <el-table-column
                                prop="user_role"
                                label="用户角色"
                                :filters="[
                                    { text: '全部', value: null },
                                    { text: '维修人员', value: 1 },
                                    { text: '业务人员', value: 2 }
                                ]"
                                :filter-method="handleRoleFilter"
                                width="150px">
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
                        v-if="filteredStaffCount > pageSize"
                        v-model:current-page="currentPage"
                        :page-size="pageSize"
                        :total="filteredStaffCount"
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