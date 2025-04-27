// src/api/modules/user.js
import request from '@/utils/request'

export const useUserApi = () => {
  // 登录
  const login = (data) => {
    return request({
      url: '/login',
      method: 'post',
      data
    })
  }
  
  // 退出登录
  const logout = () => {
    return request({
      url: '/logout',
      method: 'post'
    })
  }
  
  // 查看用户信息
  const getUserInfo = (id) => {
    return request({
      url: `/user/userInfo/${id}`,
      method: 'get'
    })
  }
  
  // 更新用户信息
  const updateUserInfo = (data) => {
    return request({
      url: `/user/userInfo/${data.id}`,
      method: 'put',
      data
    })
  }
  const getVerification = (data) => {
    return request({
      url: `/user/send-verification-code?email=${data.email}`,
      method: 'post',
    })
  }
  
  // 注册用户
  const register = (data) => {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  }
  
  return {
    login,
    logout,
    getUserInfo,
    updateUserInfo,
    register,
    getVerification
  }
}