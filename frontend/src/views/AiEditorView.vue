<template>
  <div class="page-shell">
    <h2>AI 编辑助手</h2>
    <div class="panel">
      <div class="mode-selector">
        <label>选择模式</label>
        <select v-model="mode">
          <option value="summary">摘要生成</option>
          <option value="rewrite">文本改写</option>
          <option value="titles">标题建议</option>
          <option value="seo">SEO 元信息</option>
        </select>
      </div>

      <form @submit.prevent="runAiAction">
        <template v-if="mode === 'summary'">
          <div class="field">
            <label>标题</label>
            <input v-model="summaryTitle" required />
          </div>
          <div class="field">
            <label>原始文本</label>
            <textarea v-model="summaryContent" rows="8" required />
          </div>
          <div class="field">
            <label>最大长度</label>
            <input type="number" v-model.number="summaryMaxLength" min="30" max="512" />
          </div>
        </template>

        <template v-if="mode === 'rewrite'">
          <div class="field">
            <label>改写文本</label>
            <textarea v-model="rewriteText" rows="8" required />
          </div>
          <div class="field">
            <label>风格</label>
            <select v-model="rewriteStyle">
              <option value="FORMAL">正式</option>
              <option value="CASUAL">轻松</option>
            </select>
          </div>
        </template>

        <template v-if="mode === 'titles'">
          <div class="field">
            <label>内容描述</label>
            <textarea v-model="titlesContent" rows="8" required />
          </div>
          <div class="field">
            <label>候选数量</label>
            <input type="number" v-model.number="titlesCount" min="1" max="10" />
          </div>
        </template>

        <template v-if="mode === 'seo'">
          <div class="field">
            <label>页面标题</label>
            <input v-model="seoTitle" required />
          </div>
          <div class="field">
            <label>页面内容</label>
            <textarea v-model="seoContent" rows="8" required />
          </div>
        </template>

        <div class="field actions">
          <button type="submit">执行</button>
          <button type="button" @click="reset">清空</button>
        </div>
      </form>

      <div v-if="resultText || resultList.length || seoResult" class="result-panel">
        <h3>AI 结果</h3>
        <template v-if="mode === 'titles'">
          <ul>
            <li v-for="(item, index) in resultList" :key="index">{{ item }}</li>
          </ul>
        </template>
        <template v-else-if="mode === 'seo'">
          <div class="seo-meta">
            <p><strong>标题：</strong>{{ seoResult?.title }}</p>
            <p><strong>描述：</strong>{{ seoResult?.description }}</p>
            <p><strong>关键词：</strong>{{ seoResult?.keywords }}</p>
          </div>
        </template>
        <template v-else>
          <pre>{{ resultText }}</pre>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { aiApi, AiSeoMetaResult } from '../api/ai';

const mode = ref<'summary' | 'rewrite' | 'titles' | 'seo'>('summary');
const summaryTitle = ref('AI 编辑助手');
const summaryContent = ref('');
const summaryMaxLength = ref(180);
const rewriteText = ref('');
const rewriteStyle = ref<'FORMAL' | 'CASUAL'>('FORMAL');
const titlesContent = ref('');
const titlesCount = ref(3);
const seoTitle = ref('');
const seoContent = ref('');

const resultText = ref('');
const resultList = ref<string[]>([]);
const seoResult = ref<AiSeoMetaResult | null>(null);

const reset = () => {
  summaryTitle.value = 'AI 编辑助手';
  summaryContent.value = '';
  summaryMaxLength.value = 180;
  rewriteText.value = '';
  rewriteStyle.value = 'FORMAL';
  titlesContent.value = '';
  titlesCount.value = 3;
  seoTitle.value = '';
  seoContent.value = '';
  resultText.value = '';
  resultList.value = [];
  seoResult.value = null;
};

const runAiAction = async () => {
  resultText.value = '';
  resultList.value = [];
  seoResult.value = null;

  if (mode.value === 'summary') {
    const response = await aiApi.summary({
      title: summaryTitle.value,
      content: summaryContent.value,
      maxLength: summaryMaxLength.value,
    });
    resultText.value = response.data.text;
    return;
  }

  if (mode.value === 'rewrite') {
    const response = await aiApi.rewrite({
      text: rewriteText.value,
      style: rewriteStyle.value,
    });
    resultText.value = response.data.text;
    return;
  }

  if (mode.value === 'titles') {
    const response = await aiApi.titles({
      content: titlesContent.value,
      count: titlesCount.value,
    });
    resultList.value = response.data;
    return;
  }

  if (mode.value === 'seo') {
    const response = await aiApi.seoMeta({
      title: seoTitle.value,
      content: seoContent.value,
    });
    seoResult.value = response.data;
  }
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
.mode-selector {
  margin-bottom: 24px;
}
.mode-selector label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
}
.mode-selector select,
.field input,
.field textarea,
.field select {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #d1d5db;
  border-radius: 10px;
  font-size: 1rem;
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
  resize: vertical;
  min-height: 140px;
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
.result-panel ul {
  margin: 0;
  padding-left: 20px;
}
.seo-meta p {
  margin: 10px 0;
}
</style>
