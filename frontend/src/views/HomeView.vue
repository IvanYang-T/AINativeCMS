<template>
  <div class="home-container">
    <div class="hero">
      <h1>AI 原生 CMS</h1>
      <p>统一内容管理平台，支持文章、页面、产品、分类、模板与 AI 编辑。</p>
      <div class="actions">
        <router-link to="/articles">文章</router-link>
        <router-link to="/pages">页面</router-link>
        <router-link to="/products">产品</router-link>
        <router-link to="/categories">分类</router-link>
        <router-link to="/templates">模板</router-link>
        <router-link to="/media-files">媒体</router-link>
        <router-link to="/ai-editor">AI 编辑助手</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.home-container {
  padding: 48px 24px;
}
.hero {
  max-width: 860px;
  margin: 0 auto;
  padding: 72px 32px;
  border-radius: 24px;
  background: white;
  box-shadow: 0 24px 80px rgba(16, 24, 40, 0.08);
  text-align: center;
}
.hero h1 {
  margin: 0;
  font-size: 3rem;
}
.hero p {
  margin: 24px 0;
  color: #475569;
  font-size: 1.1rem;
}
.actions {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 12px;
}
.actions a {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 130px;
  padding: 12px 18px;
  border-radius: 9999px;
  background: #2563eb;
  color: white;
  text-decoration: none;
  font-weight: 600;
}
</style>
