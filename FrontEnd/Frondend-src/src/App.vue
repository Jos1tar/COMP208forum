<script setup>
</script>

<template>

<router-view></router-view>
</template>

<style scoped>
/* .logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
} */

 /* 全局样式优化 - 添加到 App.vue 的 <style> 标签中，不要使用 scoped 属性 */

/* 基础设置 */
html, body {
  background-color: #121212;
  color: #FFFFFF;
  font-family: 'Roboto', 'Noto Sans SC', -apple-system, BlinkMacSystemFont, sans-serif;
  overflow-x: hidden;
  scroll-behavior: smooth;
}

/* 主内容区域背景 */
.v-main {
  background-color: #121212;
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(45, 45, 45, 0.4) 0%, transparent 50%),
    radial-gradient(circle at 75% 75%, rgba(25, 25, 25, 0.4) 0%, transparent 50%);
}

/* 全局滚动条美化 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.15);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.25);
}

/* 应用栏样式 */
.v-app-bar {
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  background-color: rgba(18, 18, 18, 0.8) !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

/* 侧边导航栏样式 */
.v-navigation-drawer {
  background-color: #1E1E1E !important;
  border-right: 1px solid rgba(255, 255, 255, 0.05);
}

/* 页脚样式 */
.v-footer {
  background-color: rgba(18, 18, 18, 0.8) !important;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-top: 1px solid rgba(255, 255, 255, 0.05);
}

/* 按钮基础样式 */
.v-btn {
  letter-spacing: 0.5px !important;
  text-transform: none !important;
  font-weight: 500 !important;
}

.v-btn--contained {
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.3) !important;
}

.v-btn--contained:hover {
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.4) !important;
}

/* 卡片全局样式 */
.v-card {
  overflow: hidden !important;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1) !important;
}

/* 输入框样式 */
.v-text-field--outlined .v-input__control>.v-input__slot, 
.v-text-field--filled .v-input__control>.v-input__slot {
  border-radius: 8px !important;
}

.theme--dark.v-text-field--outlined:not(.v-input--is-focused):not(.v-input--has-state)>.v-input__control>.v-input__slot fieldset {
  border-color: rgba(255, 255, 255, 0.15) !important;
}

/* 全局过渡效果 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

/* 全局对话框样式 */
.v-dialog {
  border-radius: 12px !important;
  overflow: hidden !important;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.6) !important;
}

.v-dialog .v-card {
  background-color: #1E1E1E !important;
}

/* 加载占位符样式 */
.v-skeleton-loader__image, 
.v-skeleton-loader__card-heading, 
.v-skeleton-loader__text,
.v-skeleton-loader__paragraph {
  background-color: rgba(255, 255, 255, 0.05) !important;
}

/* 选项卡样式 */
.v-tabs {
  background-color: transparent !important;
}

.v-tab {
  text-transform: none !important;
  letter-spacing: 0.5px !important;
  font-weight: 500 !important;
}

/* 提示消息样式 */
.v-snackbar__content {
  font-size: 14px !important;
}

/* 全局链接样式 */
a {
  text-decoration: none;
  color: #90CAF9;
  transition: color 0.2s ease;
}

a:hover {
  color: #64B5F6;
  text-decoration: underline;
}

/* 图片加载占位符 */
.v-image__placeholder {
  background: linear-gradient(
    135deg, 
    rgba(30, 30, 30, 1) 0%, 
    rgba(50, 50, 50, 1) 50%, 
    rgba(30, 30, 30, 1) 100%
  ) !important;
  background-size: 200% 200% !important;
  animation: gradient 1.5s ease infinite !important;
}

@keyframes gradient {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

/* 数据表格样式 */
.v-data-table {
  background-color: transparent !important;
  border-radius: 8px !important;
  overflow: hidden !important;
}

.v-data-table thead th {
  font-size: 14px !important;
  color: rgba(255, 255, 255, 0.8) !important;
  font-weight: 500 !important;
  background-color: rgba(0, 0, 0, 0.2) !important;
}

.v-data-table tbody tr {
  transition: background-color 0.2s ease;
}

.v-data-table tbody tr:hover {
  background-color: rgba(255, 255, 255, 0.05) !important;
}

/* 菜单样式 */
.v-menu__content {
  border-radius: 8px !important;
  overflow: hidden !important;
  background-color: #1E1E1E !important;
  border: 1px solid rgba(255, 255, 255, 0.05) !important;
}
</style>
