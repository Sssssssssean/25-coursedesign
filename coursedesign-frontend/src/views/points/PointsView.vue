<template>
  <div class="points-container">
    <!-- 积分概览卡片 -->
    <el-card class="points-overview">
      <template #header>
        <div class="card-header">
          <span>积分概览</span>
        </div>
      </template>
      <div class="points-info">
        <div class="points-item">
          <div class="label">成长积分</div>
          <div class="value">{{ pointsDetail.growScore }}</div>
        </div>
        <div class="points-item">
          <div class="label">可兑换积分</div>
          <div class="value">{{ pointsDetail.exchangeScore }}</div>
        </div>
        <div class="points-item">
          <div class="label">总积分</div>
          <div class="value">{{ pointsDetail.scoreTotal }}</div>
        </div>
      </div>
    </el-card>

    <!-- 用户状态卡片 -->
    <el-card class="user-status">
      <template #header>
        <div class="card-header">
          <span>用户状态</span>
        </div>
      </template>
      <div class="status-info">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="最后登录时间">
            {{ formatDate(pointsDetail.lastLoginTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="个人资料">
            <el-tag :type="pointsDetail.profileInputed ? 'success' : 'warning'">
              {{ pointsDetail.profileInputed ? '已填写' : '未填写' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="血糖记录数">
            {{ pointsDetail.bloodSugarCount }}
          </el-descriptions-item>
          <el-descriptions-item label="最后并发症记录">
            {{ formatDate(pointsDetail.lastComplicationTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="最后胰岛功能监测">
            {{ formatDate(pointsDetail.lastYdqnTime) }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>

    <!-- 当前等级和积分历史并排 -->
    <div class="level-history-row">
      <!-- 用户等级卡片 -->
      <el-card class="user-level">
        <template #header>
          <div class="card-header">
            <span>当前等级</span>
          </div>
        </template>
        <div class="level-info">
          <el-progress
            type="dashboard"
            :percentage="levelPercentage"
            :color="levelColor"
          >
            <template #default="{ percentage }">
              <div class="level-circle">
                <div class="level">{{ userLevel.level }}</div>
                <div class="points">{{ pointsDetail.growScore }}分</div>
              </div>
            </template>
          </el-progress>
        </div>
      </el-card>

      <!-- 积分历史记录 -->
      <el-card class="points-history">
        <template #header>
          <div class="card-header">
            <span>积分历史</span>
          </div>
        </template>
        <el-table :data="pointsHistory" style="width: 100%">
          <el-table-column prop="type" label="类型" width="120">
            <template #default="{ row }">
              <el-tag :type="row.type === 'growth' ? 'success' : 'warning'">
                {{ row.type === 'growth' ? '成长积分' : '可兑换积分' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="points" label="积分" width="100" />
          <el-table-column prop="description" label="描述" />
          <el-table-column prop="createTime" label="获得时间" width="180" />
        </el-table>
      </el-card>
    </div>

    <!-- 积分规则表格 -->
    <el-card class="points-rules">
      <template #header>
        <div class="card-header">
          <span>积分规则</span>
        </div>
      </template>
      <el-table :data="pointsRules" style="width: 100%">
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="behavior" label="行为" width="180" />
        <el-table-column prop="points" label="分值" width="100" />
        <el-table-column prop="limitation" label="限制" />
      </el-table>
    </el-card>

    <!-- 添加返回按钮 -->
    <div class="bottom-nav">
      <el-button @click="goBack" icon="ArrowLeft" type="primary">返回</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { getUserPointsDetail, getPointsHistory, getPointsRules, getUserLevel } from '@/api/points'
import type { UserPointsDetail, PointsHistory, PointsRule, UserLevel } from '@/types/points'
import { POINTS_RULES, LEVEL_RULES } from '@/constants/pointsRules'

const router = useRouter()

// 数据定义
const pointsDetail = ref<UserPointsDetail>({
  id: 0,
  growScore: 0,
  exchangeScore: 0,
  scoreTotal: 0,
  lastLoginTime: '',
  profileInputed: false,
  bloodSugarCount: 0,
  lastComplicationTime: '',
  lastYdqnTime: ''
})

const pointsHistory = ref<PointsHistory[]>([])
const pointsRules = ref<PointsRule[]>([])
const userLevel = ref<UserLevel>({
  level: 'C',
  points: 0
})

// 计算属性
const levelPercentage = computed(() => {
  const points = pointsDetail.value.growScore
  if (points >= LEVEL_RULES.A.min) return 100
  if (points >= LEVEL_RULES.B.min) return 70
  return 30
})

const levelColor = computed(() => {
  const points = pointsDetail.value.growScore
  if (points >= LEVEL_RULES.A.min) return LEVEL_RULES.A.color
  if (points >= LEVEL_RULES.B.min) return LEVEL_RULES.B.color
  return LEVEL_RULES.C.color
})

// 工具函数
const formatDate = (dateStr: string) => {
  if (!dateStr) return '暂无记录'
  return new Date(dateStr).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 返回函数
const goBack = () => {
  router.back()
}

// 生命周期钩子
onMounted(async () => {
  try {
    // 获取用户积分详情
    const detailData = await getUserPointsDetail()
    pointsDetail.value = detailData.data

    // 获取积分历史
    const historyData = await getPointsHistory({})
    pointsHistory.value = historyData.data

    // 获取积分规则
    const rulesData = await getPointsRules()
    pointsRules.value = rulesData.data || POINTS_RULES

    // 获取用户等级
    const levelData = await getUserLevel()
    userLevel.value = levelData.data
  } catch (error) {
    console.error('获取积分数据失败:', error)
  }
})
</script>

<style scoped>
.points-container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.points-overview {
  width: 100%;
}

.points-info {
  display: flex;
  justify-content: space-around;
  text-align: center;
}

.points-item {
  padding: 20px;
}

.points-item .label {
  font-size: 16px;
  color: #606266;
  margin-bottom: 10px;
}

.points-item .value {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.user-status {
  width: 100%;
}

.status-info {
  padding: 20px;
}

.level-history-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.user-level {
  height: 100%;
}

.points-history {
  height: 100%;
}

.level-info {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.level-circle {
  text-align: center;
}

.level {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
}

.points {
  font-size: 14px;
  color: #606266;
}

.points-rules {
  width: 100%;
}

.bottom-nav {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  padding: 20px;
}
</style> 