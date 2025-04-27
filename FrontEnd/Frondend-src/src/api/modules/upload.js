// src/api/modules/post.js
import request from '@/utils/request'

export const useUploadApi = () => {
  // 获取帖子详情
  const useUploadApi = (data) => {
    return request({
      url: '/upload',
      method: 'post',
      data
    })
  }
  

  
  return {
    useUploadApi
  }
}