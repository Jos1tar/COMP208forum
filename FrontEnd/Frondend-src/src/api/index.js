// src/api/index.js
import { usePostApi } from './modules/post'
import { useCommentApi } from './modules/comment'
import { useUserApi } from './modules/user'
import { useUploadApi } from './modules/upload'
import { chatApi } from './modules/chat'

// Export composables directly for individual use
export {
  usePostApi,
  useCommentApi,
  useUserApi,
  useUploadApi,
  chatApi
}

// Export a combined API object (alternative approach)
export const useApi = () => {
  return {
    post: usePostApi(),
    comment: useCommentApi(),
    user: useUserApi(),
    upload: useUploadApi(),
    chat: chatApi()
  }
}

// Default export
export default {
  usePostApi,
  useCommentApi,
  useUserApi,
  useUploadApi,
  useApi,
  chatApi
}