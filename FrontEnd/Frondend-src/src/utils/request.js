// src/utils/request.js
import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建axios实例
const service = axios.create({
  // baseURL: 'http://43.165.3.68:8080/',
  baseURL: 'http://118.25.14.2:8080/',
  // baseURL: 'http://localhost:8080/',
  timeout: 120000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')


    if (token !== null) {
      config.headers['authorization'] = `Bearer ${token}`;
      config.authorization = `Bearer ${token}`;
      // config.headers['ngrok-skip-browser-warning'] = "69420"
    }

    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    console.log('123',res)
    
    // 根据业务状态码处理响应
    if (res.code && res.code !== 200) {
      // 处理各种错误状态
      switch (res.code) {
        case 401:
          ElMessage.error('登录已过期，请重新登录')
          localStorage.removeItem('token')
          router.replace('/login')
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 500:
          ElMessage.error('服务器错误，请稍后再试')
          break
        default:
          ElMessage.error(res.msg || '未知错误')
      }
      return Promise.reject(res)
    }
    
    return res
  },
  error => {
    // 处理HTTP错误
    if (error.response) {
      const { status } = error.response
      
      switch (status) {
        case 401:
          ElMessage.error('登录已过期，请重新登录')
          localStorage.removeItem('token')
          router.replace('/login')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器错误，请稍后再试')
          break
        default:
          ElMessage.error(`请求失败: ${status}`)
      }
    } else if (error.message.includes('timeout')) {
      ElMessage.error('请求超时，请稍后再试')
    } else {
      ElMessage.error('网络错误，请检查网络连接')
    }
    
    return Promise.reject(error)
  }
)

export default service