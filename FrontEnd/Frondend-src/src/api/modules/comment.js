// src/api/modules/comment.js
import request from '@/utils/request'

export const useCommentApi = () => {
  // 查询父评论
  const getCommentList = (postId) => {
    return request({
      url: '/comment/commentList',
      method: 'get',
      params: { postId }
    })
  }

  
  // 查询子评论
  const getChildComments = (parentId) => {
    return request({
      url: '/comment/childComments',
      method: 'get',
      params: { parentId }
    })
  }
  
  // 添加评论
  const addComment = (data) => {
    return request({
      url: '/comment/add',
      method: 'post',
      data
    })
  }
  
  return {
    getCommentList,
    getChildComments,
    addComment
  }
}