import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const request = axios.create({
    baseURL: '/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    }
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        console.log('发送请求:', {
            url: config.url,
            method: config.method,
            data: config.data,
            headers: config.headers
        })
        // 可以在这里添加token等认证信息
        return config
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        console.log('收到响应:', {
            url: response.config.url,
            status: response.status,
            data: response.data
        })
        const res = response.data
        // 这里可以根据后端的响应结构进行调整
        if (res.code === 200) {
            return res
        } else {
            console.error('响应错误:', res)
            ElMessage.error(res.message || '请求失败')
            return Promise.reject(new Error(res.message || '请求失败'))
        }
    },
    error => {
        console.error('响应错误:', {
            url: error.config?.url,
            status: error.response?.status,
            data: error.response?.data,
            message: error.message,
            config: error.config
        })
        if (error.response) {
            // 服务器返回了错误状态码
            const message = error.response.data?.message || `请求失败 (${error.response.status})`
            ElMessage.error(message)
        } else if (error.request) {
            // 请求已发送但没有收到响应
            ElMessage.error('服务器无响应，请检查网络连接')
        } else {
            // 请求配置出错
            ElMessage.error('请求配置错误：' + error.message)
        }
        return Promise.reject(error)
    }
)

export default request