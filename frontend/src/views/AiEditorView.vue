<template>
  <div class="page-shell">
    <h2>AI 编辑助手</h2>
    <div class="panel">
      <form @submit.prevent="generateSummary">
        <div class="field">
          <label>原始文本</label>
          <textarea v-model="content" rows="8" required />
        </div>
        <div class="field actions">
          <button type="submit">生成摘要</button>
          <button type="button" @click="clear">清空</button>
        </div>
      </form>
      <div v-if="result" class="result-panel">
        <h3>AI 结果</h3>
        <pre>{{ result }}</pre>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { aiApi } from '../api/ai';

const content = ref('');
const result = ref('');

const generateSummary = async () => {
  const response = await aiApi.summary({
    title: 'AI 编辑助手',
    content: content.value,
    maxLength: 180,
  });
  result.value = response.data.text;
};

const clear = () => {
  content.value = '';
  result.value = '';
};
</script>

<style scoped>
.page-shell {
  padding: 24px;
}
.panel {
  padding: 24px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
}
.field {
  margin-bottom: 16px;
}
.field label {
  display: block;
  margin-bottom: 10px;
  font-weight: 600;
}
.field textarea {
  width: 100%;
  min-height: 180px;
  padding: 12px;
  border: 1px solid #d1d5db;
  border-radius: 12px;
  font-size: 1rem;
}
.actions {
  display: flex;
  gap: 12px;
  margin-top: 12px;
}
.actions button {
  padding: 10px 18px;
  border: none;
  border-radius: 10px;
  background: #2563eb;
  color: #fff;
  cursor: pointer;
}
.result-panel {
  margin-top: 20px;
  padding: 16px;
  background: #f8fafc;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
}
.result-panel pre {
  margin: 0;
  white-space: pre-wrap;
}
</style>
