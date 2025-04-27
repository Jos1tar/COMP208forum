<template>
  <!-- 主容器 -->
  <div class="container">
    <!-- 顶部导航栏：包含项目名称、搜索框和退出按钮 -->
    <div class="header">
      <div class="project-name">Project name</div>
      <div class="search-container">
        <input type="text" v-model="searchText" class="search-bar" placeholder="Search posts by title" @keyup.enter="searchPosts">
        <button class="search-btn" @click="searchPosts">Search</button>
        <button v-if="searchText" class="clear-btn" @click="clearSearch">Clear</button>
      </div>
      <button class="login-btn" @click="confirmLogout">Logout</button>
    </div>
    <!-- 主要内容区域：包含左侧导航、中间内容和右侧标签栏 -->
    <div class="content">
      <!-- 左侧导航栏：包含顶部按钮、添加按钮和底部按钮 -->
      <div class="sidebar">
        <!-- 顶部导航按钮 -->
        <div v-for="(item, index) in topButtons" :key="index" :class="['nav-item', { 'active': activeLeft === index }]"
          @click="selectLeft(index)">
          <i v-html="item.icon"></i> {{ item.text }}
        </div>
        <!-- 添加新帖子按钮 -->
        <div class="add-button">
          <div class="add-button-circle" @click="createNewPost">
            <span>+</span>
          </div>
        </div>
        <!-- 底部导航按钮 -->
        <div v-for="(item, index) in bottomButtons" :key="'bottom-' + index"
          :class="['nav-item', { 'active': activeLeft === topButtons.length + index }]"
          @click="selectLeft(topButtons.length + index)">
          <i v-html="item.icon"></i> {{ item.text }}
        </div>
      </div>
      <!-- 中间主要内容区域 -->
      <div class="main-content">
        <!-- 返回按钮：在帖子详情页显示 -->
        <!-- 创建新帖子界面 -->
        <div v-if="showCreatePost" class="create-post">
          <!-- 创建帖子头部：包含返回按钮、头像和用户信息 -->
          <div class="create-post-header">
            <div class="back-button">
              <button @click="showCreatePost = false">←</button>
            </div>
            <div class="avatar"></div>
            <div class="post-info">{{ currentUser?.username || 'user' }} & {{ currentUser?.major || 'N/A' }}</div>
            <div class="info-icon">ℹ️</div>
          </div>
          <!-- 帖子内容输入区域 -->
          <div class="create-post-content">
            <input v-model="newPostTitle" placeholder="Title." class="post-new-title">
            <textarea v-model="newPostContent" class="post-textarea" placeholder="What's on your mind?"></textarea>
          </div>
          <!-- 创建帖子操作区域：包含标签选择和发布按钮 -->
          <div class="create-post-actions">
            <!-- <div class="tag-selector">
              <select v-model="newPostTag" class="tag-select">
                <option value="" disabled selected>Select tag</option>
                <option v-for="tag in tags" :key="tag.id" :value="tag.id">
                  # {{ tag.text }}
                </option>
              </select>
            </div> -->
            <button class="post-btn" @click="submitNewPost" :disabled="loading">
              {{ loading ? 'Posting...' : 'Post' }}
            </button>
          </div>
          <!-- 错误信息显示 -->
          <div v-if="error" class="error">{{ error }}</div>
        </div>
        <!-- 帖子详情界面 -->
        <div v-else-if="showPostDetail" class="post-detail">
          <!-- 加载状态显示 -->
          <div v-if="loading" class="loading">Loading...</div>
          <!-- 错误信息显示 -->
          <div v-else-if="error" class="error">{{ error }}</div>
          <div v-else>
            <!-- 帖子头部：包含头像、用户信息和时间 -->
            <div class="post-header">
              <button v-if="showPostDetail" class="back-btn" @click="showPostDetail = false">←</button>
              <div class="avatar" @click.stop="openChat(currentPost)"></div>
              <div class="post-info">{{ currentPost.username || 'Anonymous' }} / {{ formatTime(currentPost.createdAt) }}</div>
              <div class="info-icon">ℹ️</div>
            </div>
            <!-- 帖子标题 -->
            <div class="post-title">
              {{ currentPost.title }}
            </div>
            <!-- 帖子内容 -->
            <div class="post-content">
              {{ currentPost.content }}
            </div>
            <!-- 帖子操作区域：包含点赞、评论和浏览数 -->
            <div class="post-actions">
              <div class="action-button" @click="likePost(currentPost.id)">
                ↑ {{ currentPost.likes }} ↓
              </div>
              <div class="action-button">
                💬
              </div>
            </div>
            <!-- 评论输入区域 -->
            <div class="comment-input">
              <input type="text" placeholder="+ comment" v-model="newComment" @keyup.enter="addComment">
              <button @click="addComment" :disabled="loading">{{ loading ? 'Posting...' : 'Comment' }}</button>
            </div>

            <!-- 评论列表区域 -->
            <div class="comments-section">
              <div v-if="currentPost.comments.length === 0" class="no-comments">
                No comments yet. Be the first to comment!
              </div>
              <div class="comment" v-for="comment in currentPost.comments" :key="comment.id">
                <div class="comment-avatar" @click.stop="openChat(comment)"></div>
                <div class="comment-content">{{ comment.content }}</div>
                <div class="comment-time">{{formatTime(comment.createdAt)  }}</div>
              </div>
            </div>
          </div>
        </div>
        <!-- 首页内容区域 -->
        <div v-else-if="activeLeft === 0">
          <!-- 加载状态显示 -->
          <div v-if="loading" class="loading">Loading posts...</div>
          <!-- 错误信息显示 -->
          <div v-else-if="error" class="error">{{ error }}</div>
          <!-- 无帖子提示 -->
          <div v-else-if="filteredPosts.length === 0" class="no-posts">No posts found</div>
          <!-- 帖子列表 -->
          <div v-else v-for="post in filteredPosts" :key="post.id" class="post" @click="viewPost(post)">
            <!-- 帖子头部 -->
            <div class="post-header">
              <div class="avatar" @click.stop="openChat(post)"></div>
              <div class="post-info">{{ post.username || 'Anonymous' }} / {{ formatTime(post.createdAt) }}</div>
              <div class="info-icon">ℹ️</div>
            </div>
            <!-- 帖子标题 -->
            <div class="post-title">{{ post.title }}</div>
            <!-- 帖子内容 -->
            <div class="post-content">
              {{ post.content }}
            </div>
            <!-- 帖子操作区域 -->
            <div class="post-actions">
              <div class="action-button" @click.stop="likePost(post.id)">
                ↑ {{ post.likes }} ↓
              </div>
              <div class="action-button">
                💬 
              </div>
            </div>
            <div class="divider"></div>
          </div>
        </div>
        <!-- 热门内容区域 -->
        <div v-else-if="activeLeft === 1">
   
          <!-- 加载状态显示 -->
          <div v-if="loading" class="loading">Loading popular posts...</div>
          <!-- 错误信息显示 -->
          <div v-else-if="error" class="error">{{ error }}</div>
          <!-- 无帖子提示 -->
          <div v-else-if="sortedPosts.length === 0" class="no-posts">No posts found</div>
          <!-- 热门帖子列表 -->
          <div v-else v-for="post in sortedPosts" :key="post.id" class="post" @click="viewPost(post)">
            <!-- 帖子头部 -->
            <div class="post-header">
              <div class="avatar" @click.stop="openChat(post)"></div>
              <div class="post-info">{{ post.username || 'Anonymous' }} / {{ formatTime(post.createdAt) }}</div>
              <div class="info-icon">ℹ️</div>
            </div>
            <!-- 帖子标题 -->
            <div class="post-title" style="">{{ post.title }}</div>
            <!-- 帖子内容 -->
            <div class="post-content">
              {{ post.content }}
            </div>
            <!-- 帖子操作区域 -->
            <div class="post-actions">
              <div class="action-button" @click.stop="likePost(post.id)">
                ↑ {{ post.likes }} ↓
              </div>
              <div class="action-button">
                💬 {{ post.comments }}
              </div>
      
            </div>
            <div class="divider"></div>
          </div>
        </div>
        <!-- 个人中心区域 -->
        <div v-else-if="activeLeft === 2">
          <div class="dashboard-content">
            <!-- 用户资料卡片 -->
            <div class="user-profile">
              <!-- 用户资料头部 -->
              <div class="profile-header">
                <div class="avatar-large">
                  <div v-if="userInfo?.avatar"><img :src="userInfo.avatar"  width="100%" height="100%" /></div>
                </div>
                <div class="profile-info">
                  <h2>{{ userInfo?.nickname || userInfo?.username }}</h2>
                  <p class="school-major">{{ userInfo?.school }} - {{ userInfo?.major }}</p>
                  <button class="edit-profile-btn" @click="openEditProfile">Edit Profile</button>
                </div>
              </div>
              <!-- 用户详细信息 -->
              <div class="profile-details">
                <div class="detail-item">
                  <span class="label">Username:</span>
                  <span class="value">{{ userInfo.username }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Email:</span>
                  <span class="value">{{ userInfo.email }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Student ID:</span>
                  <span class="value">{{ userInfo.studentId || 'Not set' }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Grade:</span>
                  <span class="value">{{ userInfo.grade || 'Not set' }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Bio:</span>
                  <span class="value">{{ userInfo.bio || 'No bio yet' }}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Member since:</span>
                  <span class="value">{{ formatTime(userInfo.createdAt) }}</span>
                </div>
              </div>
            </div>
            <!-- 用户帖子列表 -->
            <div class="my-posts">
              <h3>My Posts</h3>
              <!-- 加载状态显示 -->
              <div v-if="loading" class="loading">Loading posts...</div>
              <!-- 无帖子提示 -->
              <div v-else-if="userPosts.length === 0" class="no-posts">No posts yet</div>
              <!-- 帖子列表 -->
              <div v-else v-for="post in userPosts" :key="post.id" class="post" @click="viewPost(post)">
                <div class="post-header">
                  <div class="post-info">{{ formatTime(post.createdAt) }}</div>
                </div>
                <div class="post-content">
                  {{ post.content }}
                </div>
                <div class="post-actions">
                  <div class="action-button">
                    ↑ {{ post.likes }} ↓
                  </div>
                  <div class="action-button">
                    💬
                  </div>
           
                </div>
                <div class="divider"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 消息通知区域 -->
        <div v-else-if="activeLeft === 3">
          <h2>Information</h2>
          <!-- 加载状态显示 -->
          <div v-if="loading" class="loading">Loading notifications...</div>
          <div v-else class="notifications">
            <!-- 无通知提示 -->
            <div v-if="notifications.length === 0" class="no-notifications">No notifications</div>
            <!-- 通知列表 -->
            <div v-else v-for="(notification, index) in notifications" :key="index" class="notification-item">
              <div class="notification-icon">{{ notification.type === 'system' ? '🔔' : '💬' }}</div>
              <div class="notification-content">
                <div class="notification-title">{{ notification.title }}</div>
                <div class="notification-text">{{ notification.content }}</div>
                <div class="notification-time">{{ formatTime(notification.createdAt) }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧标签栏 -->
      <div v-if="!showPostDetail" class="tags-sidebar">
        <div v-for="(tag, index) in tags" :key="index" :class="['tag', { 'active': activeTag === index }]"
          @click="selectTag(index)">
          # {{ tag.text }}
        </div>
      </div>
    </div>
  </div>

  <!-- 编辑用户信息弹窗 -->
  <div v-if="showEditProfile" class="edit-profile-overlay">
    <div class="edit-profile-dialog">
      <h2>Edit Profile</h2>
      <div class="edit-form">
        <div class="form-item">
          <label>Nickname</label>
          <input type="text" v-model="editForm.nickname" placeholder="Enter your nickname">
        </div>
        <div class="form-item">
          <label>School</label>
          <input type="text" v-model="editForm.school" placeholder="Enter your school">
        </div>
        <div class="form-item">
          <label>Major</label>
          <input type="text" v-model="editForm.major" placeholder="Enter your major">
        </div>
        <div class="form-item">
          <label>Email</label>
          <input type="email" v-model="editForm.email" placeholder="Enter your email">
        </div>
        <div class="form-item">
          <label>Bio</label>
          <textarea v-model="editForm.bio" placeholder="Tell us about yourself"></textarea>
        </div>
      </div>
      <div class="dialog-actions">
        <button class="cancel-btn" @click="showEditProfile = false">Cancel</button>
        <button class="save-btn" @click="saveUserProfile" :disabled="loading">
          {{ loading ? 'Saving...' : 'Save' }}
        </button>
      </div>
    </div>
  </div>

  <!-- 聊天室弹窗 -->
  <div v-if="showChat" class="chat-overlay">
    <div class="chat-dialog">
      <div class="chat-header">
        <button class="back-btn" @click="showChat = false">←</button>
        <h3>Chat with {{ currentChatUser?.username }}</h3>
      </div>
      
      <div class="chat-messages" ref="chatContainer" v-if="!chatLoading">
        <div v-for="message in chatMessages" :key="message.id" 
             :class="['message', message.isMine ? 'sent' : 'received']">
          <div class="message-avatar">
            <img :src="message.isMine ? message.senderAvatar : message.receiverAvatar" 
                 :alt="message.isMine ? message.senderUsername : message.receiverUsername">
          </div>
          <div class="message-content">
            <div class="message-text">{{ message.content }}</div>
            <div class="message-time">{{ formatTime(message.createTime) }}</div>
          </div>
        </div>
      </div>
      <div v-else class="loading">Loading messages...</div>
      
      <div class="chat-input">
        <input type="text" v-model="newMessage" placeholder="Type a message..." @keyup.enter="sendMessage">
        <button @click="sendMessage">Send</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { ElMessageBox } from 'element-plus';
import {
  usePostApi,
  useCommentApi,
  useUserApi,
  useUploadApi,
  chatApi
} from '../api/index';

// 初始化API
const postApi = usePostApi();    // 帖子相关API
const commentApi = useCommentApi();  // 评论相关API
const userApi = useUserApi();    // 用户相关API
const uploadApi = useUploadApi();    // 上传相关API
const chatApiInstance = chatApi();   // 聊天相关API

// 用户状态管理
const currentUser = ref(null);   // 当前登录用户信息
const userPosts = ref([]);   // 用户发布的帖子列表
const notifications = ref([]);   // 用户通知列表

// 搜索功能
const searchText = ref('');  // 搜索框输入内容

// 帖子数据
const posts = ref([]);   // 帖子列表
const loading = ref(false);  // 加载状态
const error = ref(null); // 错误信息

// 视图状态
const showPostDetail = ref(false);  // 是否显示帖子详情
const showCreatePost = ref(false);  // 是否显示创建帖子界面
const currentPost = ref({});    // 当前查看的帖子详情
const newComment = ref(''); // 新评论内容

// 新帖子状态
const newPostContent = ref(''); // 新帖子内容
const newPostTitle = ref('');
const newPostTag = ref(''); // 新帖子标签

// 左侧导航按钮
const topButtons = ref([
  { text: 'HOME', icon: '🏠' },    // 首页
  { text: 'POPULAR', icon: '📈' }  // 热门
]);

const bottomButtons = ref([
  { text: 'DASHBOARD', icon: '👤' }  // 个人中心
]);

// 标签列表
const tags = ref([]);

// 活动状态
const activeLeft = ref(0);  // 当前选中的左侧导航项
const activeTag = ref(null);    // 当前选中的标签
const sortOrder = ref(true);    // 排序顺序（true为升序，false为降序）

// 用户信息统计
const userInfo = ref(null); // 用户详细信息
const totalComments = ref(0);   // 总评论数
const totalLikes = ref(0);  // 总点赞数

// 用户信息编辑相关
const showEditProfile = ref(false);
const editForm = ref({
  nickname: '',
  school: '',
  major: '',
  bio: '',
  email: ''
});

// 聊天相关状态
const showChat = ref(false);
const currentChatUser = ref(null);
const chatMessages = ref([]);
const newMessage = ref('');
const chatLoading = ref(false);
const chatContainer = ref(null);

// 退出登录确认
const confirmLogout = () => {
  ElMessageBox.confirm(
    'Are you sure you want to logout?',
    'Logout Confirmation',
    {
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      type: 'warning',
    }
  )
    .then(() => {
      logout();
    })
    .catch(() => {
      // 用户点击取消，不做任何操作
    });
};

// 执行退出登录
const logout = async () => {
  try {
    // 调用退出登录接口
    await userApi.logout();
    // 清除本地存储
    localStorage.removeItem('token');
    localStorage.removeItem('userInfo');
    currentUser.value = null;
    userPosts.value = [];
    // 跳转到登录页面
    window.location.href = '/';
  } catch (err) {
    console.error("Logout failed:", err);
  }
};

// 获取用户信息
const getUserInfo = async () => {
  try {
    const storedUserInfo = localStorage.getItem('userInfo');
    if (storedUserInfo) {
      currentUser.value = JSON.parse(storedUserInfo);
      userInfo.value = JSON.parse(storedUserInfo);
    }
    console.log("storedUserInfo",currentUser.value)
    
    // 获取用户帖子
    if (activeLeft.value === 2) {
      await getUserPosts();
    }
  } catch (err) {
    console.error("Failed to get user info:", err);
  }
};

// 获取用户发布的帖子
const getUserPosts = async () => {
  try {
    loading.value = true;
    const response = await postApi.getMyPosts(currentUser.value.id);
    userPosts.value = response.data;
    calculateStats();
    loading.value = false;
  } catch (err) {
    console.error("Failed to get user posts:", err);
    loading.value = false;
  }
};

// 获取帖子列表
const fetchPosts = async () => {
  try {
    loading.value = true;
    error.value = null;
    
    const response = await postApi.getPostList();
    posts.value = response.data.rows;
    console.log("list",posts.value);
    
    loading.value = false;
  } catch (err) {
    error.value = "Failed to load posts. Please try again.";
    loading.value = false;
  }
};

// 获取标签列表
const fetchTags = async () => {
  // try {
  //   const response = await postApi.getAllTags();
  //   tags.value = response;
  // } catch (err) {
  //   console.error("Failed to get tags:", err);
  // }
};

// 查看帖子详情
const viewPost = async (post) => {
  try {
    loading.value = true;
    error.value = null;
    
    // 获取帖子详情
    const postResponse = await postApi.getPostDetail(post.id);
    currentPost.value = postResponse.data;
    
    // 获取评论列表
    const commentsResponse = await commentApi.getCommentList(post.id);
    currentPost.value.comments = commentsResponse.data.rows;
    
    showPostDetail.value = true;
    showCreatePost.value = false;
    loading.value = false;
  } catch (err) {
    error.value = "Failed to load post details.";
    loading.value = false;
  }
};

// 添加评论
const addComment = async () => {
  if (!newComment.value.trim()) {
    return;
  }

  try {
    loading.value = true;
    error.value = null;
    console.log("userInfo.value",userInfo.value)
    await commentApi.addComment({
      postId: currentPost.value.id,
      content: newComment.value,
      userId:userInfo.value.id
    });
    
    // 重新获取评论列表
    const commentsResponse = await commentApi.getCommentList(currentPost.value.id);
    console.log("commentsResponse",commentsResponse.data.rows)
    currentPost.value.comments = commentsResponse.data.rows;
    
    
    // 重置输入
    newComment.value = '';
    loading.value = false;
  } catch (err) {
    error.value = "Failed to add comment.";
    loading.value = false;
  }
};

// 创建新帖子
const createNewPost = () => {
  showCreatePost.value = true;
  showPostDetail.value = false;
  newPostContent.value = '';
  newPostTag.value = '';
  newPostTitle.value = '';
};

// 提交新帖子
const submitNewPost = async () => {
  if (!newPostContent.value.trim()) {
    error.value = "Post content cannot be empty";
    return;
  }

  try {
    loading.value = true;
    error.value = null;

    const data = {
      content: newPostContent.value,
      tag: newPostTag.value || "无标签",
      title:newPostTitle.value || "无标题",
      userId: userInfo.value.id,
      userName: userInfo.value.nickname
    };
    console.log(data)
    await postApi.createPost(data);
    
    // 重新获取帖子列表
    await fetchPosts();
    
    // 重置表单
    showCreatePost.value = false;
    newPostContent.value = '';
    newPostTag.value = '';
    loading.value = false;
  } catch (err) {
    error.value = "Failed to create post.";
    loading.value = false;
  }
};

// 点赞帖子
const likePost = async (postId) => {
  try {
    await postApi.likePost({ postId });
    
    // 如果在详情页面，更新当前帖子
    if (showPostDetail.value && currentPost.value.id === postId) {
      console.log("112233")
      const postResponse = await postApi.getPostDetail(postId);
      console.log("postResponse",postResponse.data)
      currentPost.value = postResponse.data;
    } else {
      // 否则更新列表
      await fetchPosts();
    }
  } catch (err) {
    console.error("Failed to like post:", err);
  }
};

// 获取通知列表
const fetchNotifications = async () => {
  try {
    loading.value = true;
    const response = await userApi.getNotifications();
    notifications.value = response;
    loading.value = false;
  } catch (err) {
    console.error("Failed to get notifications:", err);
    loading.value = false;
  }
};

// 切换排序顺序
const toggleSortOrder = () => {
  sortOrder.value = !sortOrder.value;
};

// 选择左侧导航项
const selectLeft = async (index) => {
  activeLeft.value = index;
  showPostDetail.value = false;
  showCreatePost.value = false;
  error.value = null;
  
  // 根据选中的标签页获取不同的数据
  if (index === 0) { // HOME
    await fetchPosts();
  } else if (index === 1) { // POPULAR
    await fetchPopularPosts();
  } else if (index === 2) { // DASHBOARD
    await getUserPosts();
  } else if (index === 3) { // INFORMATION
    await fetchNotifications();
  }
};

// 获取热门帖子
const fetchPopularPosts = async () => {
  try {
    loading.value = true;
    error.value = null;
    
    // 使用与首页相同的帖子列表，只是排序方式不同
    const response = await postApi.getPostList();
    posts.value = response.data.rows;
    
    loading.value = false;
  } catch (err) {
    error.value = "Failed to load popular posts.";
    loading.value = false;
  }
};

// 选择标签
const selectTag = async (index) => {
  // 切换标签选择
  activeTag.value = activeTag.value === index ? null : index;
  
  // 根据标签筛选帖子
  if (activeTag.value !== null) {
    try {
      loading.value = true;
      error.value = null;
      
      const tagId = tags.value[activeTag.value].id;
      const response = await postApi.getPostsByTag(tagId);
      posts.value = response;
      
      loading.value = false;
    } catch (err) {
      error.value = "Failed to load posts for this tag.";
      loading.value = false;
    }
  } else {
    // 重置为所有帖子
    fetchPosts();
  }
};

// 搜索帖子
const searchPosts = async () => {
  if (searchText.value.trim()) {
    try {
      loading.value = true;
      error.value = null;
      
      const response = await postApi.getPostList({
        keyword: searchText.value.trim()
      });
      
      posts.value = response.data.rows;
      
      loading.value = false;
    } catch (err) {
      error.value = "Search failed.";
      loading.value = false;
    }
  } else {
    // 清空搜索，重置为所有帖子
    await fetchPosts();
  }
};

// 清空搜索
const clearSearch = () => {
  searchText.value = '';
  fetchPosts();
};

// 时间格式化函数
const formatTime = (timestamp) => {
  console.log("timestamp",timestamp)
  if (!timestamp) return 
  
  const date = new Date(timestamp);
  const now = new Date();
  const diff = now - date;
  
  // 如果小于1小时，显示分钟
  if (diff < 60 * 60 * 1000) {
    const minutes = Math.floor(diff / (60 * 1000));
    return `${minutes}m ago`;
  }
  
  // 如果小于1天，显示小时
  if (diff < 24 * 60 * 60 * 1000) {
    const hours = Math.floor(diff / (60 * 60 * 1000));
    return `${hours}h ago`;
  }
  
  // 否则显示日期
  return date.toLocaleDateString();
};

// 计算用户统计数据
const calculateStats = () => {
  if (userPosts.value) {
    totalComments.value = userPosts.value.reduce((sum, post) => {
      return sum + (post.comments ? post.comments.length : 0);
    }, 0);
    
    totalLikes.value = userPosts.value.reduce((sum, post) => {
      return sum + (post.likes || 0);
    }, 0);
  }
};

// 打开编辑弹窗
const openEditProfile = () => {
  editForm.value = {
    id:userInfo.value.id,
    nickname: userInfo.value?.nickname || '',
    school: userInfo.value?.school || '',
    major: userInfo.value?.major || '',
    bio: userInfo.value?.bio || '',
    email: userInfo.value?.email || ''
  };
  showEditProfile.value = true;
};

// 保存用户信息
const saveUserProfile = async () => {
  try {
    loading.value = true;
    error.value = null;
    
    await userApi.updateUserInfo(editForm.value);
    
    // 更新本地用户信息
    userInfo.value = {
      ...userInfo.value,
      ...editForm.value
    };
    
    showEditProfile.value = false;
    loading.value = false;
  } catch (err) {
    error.value = "Failed to update profile.";
    loading.value = false;
  }
};

// 打开聊天室
const openChat = async (user) => {
  currentChatUser.value = user;
  showChat.value = true;
  await fetchChatMessages();
  // 等待DOM更新后滚动到底部
  setTimeout(() => {
    scrollToBottom();
  }, 100);
};

// 获取聊天记录
const fetchChatMessages = async () => {
  try {
    chatLoading.value = true;
    const response = await chatApiInstance.messageList({
      page: 1,
      pageSize: 999,
      userId: userInfo.value.id,
      targetUserId: currentChatUser.value.id
    });
    // 反转消息列表，使最新的消息在底部
    chatMessages.value = response.data.rows.reverse();
    chatLoading.value = false;
  } catch (err) {
    console.error("Failed to fetch chat messages:", err);
    chatLoading.value = false;
  }
};

// 发送消息
const sendMessage = async () => {
  if (!newMessage.value.trim()) return;
  
  try {
    await chatApiInstance.sendMessage({
      receiverId: currentChatUser.value.id,
      content: newMessage.value.trim()
    });
    
    // 清空输入框
    newMessage.value = '';
    
    // 重新获取消息列表
    await fetchChatMessages();
    // 滚动到底部
    scrollToBottom();
  } catch (err) {
    console.error("Failed to send message:", err);
  }
};

// 滚动到底部
const scrollToBottom = () => {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
};

// 页面初始化
onMounted(async () => {
  await getUserInfo()
  // 获取初始数据
  await fetchPosts();
  await fetchTags();
});

// 计算属性
const filteredPosts = computed(() => {
  return posts.value;
});

const sortedPosts = computed(() => {
  const postsToSort = [...posts.value];
  
  // 根据时间排序
  postsToSort.sort((a, b) => {
    const timeA = new Date(a.createdAt).getTime();
    const timeB = new Date(b.createdAt).getTime();
    
    // 热门页面默认按时间降序（最新在前）
    return sortOrder.value ? timeA - timeB : timeB - timeA;
  });
  
  return postsToSort;
});
</script>

<style scoped>
.container {
  width: 100%;
  max-width: 1200px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin: 20px auto;
}

.header {

  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #eaeaea;
}

.project-name {
  font-size: 22px;
  font-weight: bold;
  color: #333;
}

.search-container {
  flex: 1;
  max-width: 800px;
  margin: 0 20px;
  display: flex;
  gap: 10px;
}

.search-bar {
  flex: 1;
  padding: 10px 20px 10px 50px;
  border: 2px solid #eaeaea;
  border-radius: 50px;
  font-size: 16px;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>');
  background-repeat: no-repeat;
  background-position: 15px center;
  background-size: 24px;
  outline: none;
}

.search-btn {
  padding: 10px 20px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
}

.clear-btn {
  padding: 10px 20px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 50px;
  cursor: pointer;
}

.clear-btn:hover {
  background-color: #e0e0e0;
}

.login-btn {
  padding: 10px 20px;
  background-color: white;
  border: 2px solid #333;
  border-radius: 50px;
  font-size: 16px;
  cursor: pointer;
  font-weight: bold;
}

.content {
  display: flex;
  min-height: calc(100vh - 60px);
}

.sidebar {
  width: 250px;
  padding: 20px;
  border-right: 1px solid #eaeaea;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 25px;
  cursor: pointer;
  font-weight: bold;
  transition: all 0.3s ease;
}

.nav-item:hover {
  background-color: #f5f5f5;
}

.nav-item.active {
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
}

.nav-item i {
  margin-right: 10px;
}

.add-button {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.add-button-circle {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 3px solid #000;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.add-button-circle span {
  font-size: 50px;
  font-weight: bold;
}

.divider {
  height: 1px;
  background-color: #eaeaea;
  margin: 20px 0;
}

.main-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  position: relative;
}

.back-button {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.back-button button {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 1px solid #eaeaea;
  background: none;
  font-size: 20px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.create-post {
  border-radius: 8px;
}

.create-post-header {
  display: flex;
  align-items: center;
  padding: 10px 15px;
  border-radius: 50px;
  border: 1px solid #eaeaea;
  margin-bottom: 10px;
}

.create-post-content {
  margin-bottom: 15px;
}

.post-textarea {
  width: 95%;
  min-height: 200px;
  padding: 20px;
  background-color: #eee;
  border-radius: 8px;
  border: none;
/*  resize: vertical;*/
  font-size: 16px;
  outline: none;

  resize: none;
}

.create-post-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.tag-selector {
  flex: 1;
  margin-right: 10px;
}

.tag-select {
  width: 100%;
  padding: 10px;
  border-radius: 20px;
  border: 1px solid #ddd;
  outline: none;
}

.post-btn {
  padding: 10px 30px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 20px;
  font-weight: bold;
  cursor: pointer;
}

.post-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.post {
  border-radius: 8px;
  margin-bottom: 20px;
  cursor: pointer;
}

.post-header {
  display: flex;
  align-items: center;
  align-items: center;
  padding: 10px 15px;
  border-radius: 50px;
  border: 1px solid #eaeaea;
  margin-bottom: 10px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #ddd;
  margin-right: 15px;
}

.post-info {
  flex: 1;
}

.info-icon {
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  cursor: pointer;
}


.post-content {
  padding: 20px;
  background-color: #eee;
  border-radius: 8px;
  min-height: 200px;
  margin-bottom: 15px;
}

.post-actions {
  display: flex;
  gap: 10px;
}

.action-button {
  padding: 8px 20px;
  border: 1px solid #ddd;
  border-radius: 30px;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.sort-controls {
  margin-bottom: 20px;
}

.sort-button {
  padding: 8px 16px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 20px;
  cursor: pointer;
}

.comment-input {
  margin: 20px 0;
  display: flex;
}

.comment-input input {
  flex: 1;
  padding: 12px 20px;
  border: 1px solid #ddd;
  border-radius: 30px;
  font-size: 16px;
  outline: none;
  margin-right: 10px;
}

.comment-input button {
  padding: 0 20px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 30px;
  font-weight: bold;
  cursor: pointer;
}

.comment-input button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.comments-section {
  margin-top: 20px;
}

.comment {
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 8px;
  margin-bottom: 10px;
}

.comment-content {
  margin-bottom: 5px;
  color: #333;
}

.comment-time {
  font-size: 12px;
  color: #666;
}

.tags-sidebar {
  width: 250px;
  padding: 20px;
  border-left: 1px solid #eaeaea;
  background-color: #f5f5f5;
}

.tag {
  padding: 12px;
  background-color: white;
  border-radius: 8px;
  margin-bottom: 10px;
  cursor: pointer;
  text-align: center;
  font-weight: bold;
  transition: all 0.3s ease;
}

.tag:hover,
.tag.active {
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
}

.dashboard-content {
  padding: 20px;
}

.user-profile {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar-large {
  width: 100px;
  height: 100px;
  background-color: #ddd;
  margin-right: 20px;
}

.profile-info h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.school-major {
  color: #666;
  margin-top: 5px;
}

.profile-details {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
  margin-top: 20px;
}

.detail-item {
  display: flex;
  flex-direction: column;
}

.label {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.value {
  font-size: 16px;
  color: #333;
}

.user-stats {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  flex: 1;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.stat-card h3 {
  margin: 0;
  font-size: 16px;
  color: #666;
}

.stat-card p {
  margin: 10px 0 0;
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.my-posts {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.my-posts h3 {
  margin: 0 0 20px;
  font-size: 20px;
  color: #333;
}

.notifications {
  margin-top: 20px;
}

.notification-item {
  display: flex;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 8px;
  margin-bottom: 10px;
}

.notification-icon {
  margin-right: 15px;
  font-size: 20px;
}

.notification-title {
  font-weight: bold;
  margin-bottom: 5px;
}

.notification-time {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
}

.login-form-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.login-form {
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.login-form h2 {
  margin-bottom: 20px;
  text-align: center;
}

.login-form input {
  width: 100%;
  padding: 12px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.login-submit-btn {
  padding: 12px 20px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  flex: 1;
  margin-right: 10px;
}

.login-submit-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.cancel-btn {
  padding: 12px 20px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.register-link {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
}

.register-link a {
  color: #ff7e5f;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}

.error {
  color: #e74c3c;
  background-color: #fde8e7;
  padding: 10px;
  border-radius: 5px;
  margin: 10px 0;
}

.no-posts, .no-comments, .no-notifications {
  padding: 20px;
  text-align: center;
  color: #666;
}

.login-notice {
  text-align: center;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 8px;
  margin: 15px 0;
}

.login-notice button {
  margin-top: 10px;
  padding: 8px 16px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.back-btn {
 
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid #eaeaea;
  background: none;
  font-size: 20px;
  margin-right: 20px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.post-title {
  font-size: 18px;
  font-weight: bold;
  margin: 10px 0;
  color: #333;
}

.edit-profile-btn {
  padding: 8px 16px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  margin-top: 10px;
}

.edit-profile-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.edit-profile-dialog {
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.edit-profile-dialog h2 {
  margin-bottom: 20px;
  text-align: center;
}

.edit-form {
  margin-bottom: 20px;
}

.form-item {
  margin-bottom: 15px;
}

.form-item label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

.form-item input,
.form-item textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

.form-item textarea {
  height: 100px;
  resize: vertical;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.cancel-btn {
  padding: 10px 20px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
}

.save-btn {
  padding: 10px 20px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.save-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.avatar, .comment-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #ddd;
  margin-right: 15px;
  cursor: pointer;
}

.chat-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.chat-dialog {
  background-color: white;
  width: 100%;
  max-width: 600px;
  height: 80vh;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  position: relative;
}

.chat-header {
  padding: 15px;
  border-bottom: 1px solid #eee;
  display: flex;
  align-items: center;
  background-color: white;
  position: sticky;
  top: 0;
  z-index: 1;
}

.chat-header h3 {
  margin: 0;
  margin-left: 10px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 15px;
  padding-bottom: 80px;
  display: flex;
  flex-direction: column;
}

.message {
  display: flex;
  margin-bottom: 15px;
  max-width: 70%;
}

.message.sent {
  margin-left: auto;
  flex-direction: row-reverse;
}

.message.received {
  margin-right: auto;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  margin: 0 10px;
}

.message-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.message-content {
  display: flex;
  flex-direction: column;
}

.message.sent .message-content {
  align-items: flex-end;
}

.message.received .message-content {
  align-items: flex-start;
}

.message-text {
  padding: 10px 15px;
  border-radius: 15px;
  background-color: #f0f0f0;
  word-break: break-word;
}

.message.sent .message-text {
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
}

.message-time {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
}

.chat-input {
  padding: 15px;
  border-top: 1px solid #eee;
  display: flex;
  gap: 10px;
  background-color: white;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1;
}

.chat-input input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 20px;
  outline: none;
}

.chat-input button {
  padding: 10px 20px;
  background: linear-gradient(90deg, #ff7e5f, #feb47b);
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

.chat-input button:hover {
  opacity: 0.9;
}

.chat-input button:active {
  transform: scale(0.98);
}

.comment {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
}

.comment-content {
  flex: 1;
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 8px;
  margin-right: 10px;
}


.post-new-title{
  font-size: 20px;
  font-weight: bold;
  margin: 10px 0;
  color: #333;

  width: 94%;
  min-height: 30px;

  background-color: #eee;
  border-radius: 8px;

  padding: 10px;

  outline: none;

  border: none;
}
</style>