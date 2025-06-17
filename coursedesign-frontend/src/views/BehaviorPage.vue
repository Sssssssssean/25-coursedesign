<template>
  <div class="behavior-container">
    <!-- 添加导航栏 -->
    <div class="nav-bar">
      <el-button @click="goBack" icon="ArrowLeft">返回</el-button>
      <el-button type="primary" @click="goToPoints" icon="Star">查看积分</el-button>
    </div>

    <el-row :gutter="20">
      <!-- 血糖记录 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>血糖记录</span>
              <el-tag type="success" v-if="bloodSugarCount >= 3">已获得积分</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>当前记录数：{{ bloodSugarCount }}</p>
            <el-button type="primary" @click="handleBloodSugar">
              记录血糖
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 并发症记录 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>并发症记录</span>
              <el-tag type="success" v-if="canRecordComplication">可记录</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>上次记录：{{ formatDate(lastComplicationTime) }}</p>
            <el-button type="primary" @click="handleComplication" :disabled="!canRecordComplication">
              记录并发症
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 评估报告 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>评估报告</span>
              <el-tag type="success" v-if="canGenerateReport">可生成</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>血糖记录数：{{ bloodSugarCount }}/10</p>
            <el-button type="primary" @click="handleReport" :disabled="!canGenerateReport">
              生成报告
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 胰岛功能监测 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>胰岛功能监测</span>
              <el-tag type="success" v-if="canMonitorYdgn">可监测</el-tag>
            </div>
          </template>
          <div class="card-content">
            <p>上次监测：{{ formatDate(lastYdgnTime) }}</p>
            <el-button type="primary" @click="handleYdgn" :disabled="!canMonitorYdgn">
              开始监测
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 门诊随访 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>门诊随访</span>
            </div>
          </template>
          <div class="card-content">
            <p>完成随访可获得3分可兑换积分</p>
            <el-button type="primary" @click="handleFollowUp">
              开始随访
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 扩展活动 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>扩展活动</span>
            </div>
          </template>
          <div class="card-content">
            <p>参加活动可获得5分可兑换积分</p>
            <el-button type="primary" @click="handleActivity">
              查看活动
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 科研招募 -->
      <el-col :span="8">
        <el-card class="behavior-card">
          <template #header>
            <div class="card-header">
              <span>科研招募</span>
            </div>
          </template>
          <div class="card-content">
            <p>参加研究可获得8分可兑换积分</p>
            <el-button type="primary" @click="handleResearch">
              查看招募
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 各种弹窗组件 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="50%">
      <component
        :is="currentDialog"
        v-if="dialogVisible"
        :currentCount="bloodSugarCount"
        @close="dialogVisible = false"
        @success="handleDialogSuccess"
      />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import type { UserPointsDetail } from '@/types/points'
import BloodSugarForm from '@/components/BloodSugarForm.vue'
import { getUserById } from '@/api'

const router = useRouter()

// 用户数据
const userData = ref<UserPointsDetail>({
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

// 计算属性
const bloodSugarCount = computed(() => userData.value.bloodSugarCount)
const lastComplicationTime = computed(() => userData.value.lastComplicationTime)
const lastYdgnTime = computed(() => userData.value.lastYdqnTime)

const canRecordComplication = computed(() => {
  if (!lastComplicationTime.value) return true
  const lastDate = new Date(lastComplicationTime.value)
  const now = new Date()
  return now.getFullYear() > lastDate.getFullYear()
})

const canGenerateReport = computed(() => {
  return userData.value.profileInputed && userData.value.bloodSugarCount >= 10
})

const canMonitorYdgn = computed(() => {
  if (!lastYdgnTime.value) return true
  const lastDate = new Date(lastYdgnTime.value)
  const now = new Date()
  const diffMonths = (now.getFullYear() - lastDate.getFullYear()) * 12 + 
    now.getMonth() - lastDate.getMonth()
  return diffMonths >= 3
})

// 弹窗控制
const dialogVisible = ref(false)
const dialogTitle = ref('')
const currentDialog = ref<any>(null)

// 工具函数
const formatDate = (dateStr: string) => {
  if (!dateStr) return '暂无记录'
  return new Date(dateStr).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

// 导航函数
const goBack = () => {
  router.back()
}

const goToPoints = () => {
  router.push('/points')
}

// 处理函数
const handleBloodSugar = () => {
  dialogTitle.value = '记录血糖'
  currentDialog.value = BloodSugarForm
  dialogVisible.value = true
}

const handleComplication = () => {
  dialogTitle.value = '记录并发症'
  currentDialog.value = 'ComplicationForm'
  dialogVisible.value = true
}

const handleReport = async () => {
  try {
    const res = await generateReport()
    if (res.code === 200) {
      ElMessage.success('评估报告生成成功')
      refreshUserData()
    }
  } catch (error) {
    console.error('生成报告失败:', error)
  }
}

const handleYdgn = () => {
  dialogTitle.value = '胰岛功能监测'
  currentDialog.value = 'YdgnForm'
  dialogVisible.value = true
}

const handleFollowUp = async () => {
  try {
    const res = await followUp()
    if (res.code === 200) {
      ElMessage.success('随访记录成功')
      refreshUserData()
    }
  } catch (error) {
    console.error('随访记录失败:', error)
  }
}

const handleActivity = () => {
  dialogTitle.value = '扩展活动'
  currentDialog.value = 'ActivityList'
  dialogVisible.value = true
}

const handleResearch = () => {
  dialogTitle.value = '科研招募'
  currentDialog.value = 'ResearchList'
  dialogVisible.value = true
}

const handleDialogSuccess = () => {
  // 立即更新本地记录数
  userData.value.bloodSugarCount += 1
  // 关闭对话框
  dialogVisible.value = false
  // 异步刷新完整数据
  refreshUserData().catch(error => {
    console.error('刷新用户数据失败:', error)
  })
}

// 获取用户数据
const refreshUserData = async () => {
  try {
    const userId = localStorage.getItem('userId')
    if (userId) {
      const res = await getUserById(userId)
      if (res.code === 200) {
        userData.value = res.data
      }
    }
  } catch (error) {
    console.error('获取用户数据失败:', error)
  }
}

// 在组件挂载时获取用户数据
onMounted(() => {
  refreshUserData()
})
</script>

<style scoped>
.behavior-container {
  padding: 20px;
}

.nav-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.behavior-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  text-align: center;
}

.card-content p {
  margin-bottom: 15px;
  color: #606266;
}
</style> 