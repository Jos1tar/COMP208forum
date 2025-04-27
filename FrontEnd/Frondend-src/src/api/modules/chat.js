// src/api/modules/comment.js
import request from '@/utils/request'


export const chatApi = () => {
    // 发送私聊消息
// data:   {
    //    "receiverId": "",    
    //    "content": ""  
//    }
  const sendMessage = (data) => {
    return request({
      url: '/message/send',
      method: 'post',
      data
    })
  }

  //获取聊天记录
    //params: {
    //    page: 1,
    //    pageSize:999,
    //    userId: '',
    // targetUserId:""
    //}
  const messageList = (params) => {
    return request({
      url: `message/list/${params.targetUserId}`,
      method: 'get',
      params
    })
  }
  
 
  
  return {
    sendMessage,
    messageList,
  }
}