<template>
  <div class="page-shell">
    <h2>页面管理</h2>
    <div class="panel">
      <form @submit.prevent="savePage">
        <div class="field">
          <label>标题</label>
          <input v-model="form.title" required />
        </div>
        <div class="field">
          <label>URL 路径</label>
          <input v-model="form.urlPath" required />
        </div>
        <div class="field">
          <label>内容</label>
          <textarea v-model="form.content" rows="8" required />
        </div>
        <div class="field actions">
          <button type="submit">{{ form.id ? '更新' : '创建' }}</button>
          <button type="button" @click="resetForm">清空</button>
        </div>
      </form>
    </div>

    <div class="panel table-panel">
      <h3>页面列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>标题</th>
            <th>路径</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in pages" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.urlPath }}</td>
            <td>
              <button @click="editPage(item)">编辑</button>
              <button @click="deletePage(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { pageApi } from '../api/page';

type PageForm = {
  id?: number;
  title: string;
  urlPath: string;
  content: string;
  status: 'DRAFT' | 'PUBLISHED';
};

const pages = ref<Array<Record<string, any>>>([]);
const form = reactive<PageForm>({
  title: '',
  urlPath: '',
  content: '',
  status: 'DRAFT',
});

const loadPages = async () => {
  const response = await pageApi.list();
  pages.value = response.data;
};

const resetForm = () => {
  form.id = undefined;
  form.title = '';
  form.urlPath = '';
  form.content = '';
  form.status = 'DRAFT';
};

const savePage = async () => {
  if (form.id) {
    await pageApi.update(form.id, form as any);
  } else {
    await pageApi.create(form as any);
  }
  resetForm();
  await loadPages();
};

const editPage = (item: any) => {
  form.id = item.id;
  form.title = item.title;
  form.urlPath = item.urlPath;
  form.content = item.content || '';
  form.status = item.status || 'DRAFT';
};

const deletePage = async (id: number) => {
  await pageApi.remove(id);
  await loadPages();
};

onMounted(loadPages);
</script>

<style scoped>
.page-shell {
  padding: 24px;
}
.panel {
  margin-bottom: 24px;
  padding: 20px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
}
.field {
  margin-bottom: 16px;
}
.field label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
}
.field input,
.field textarea {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #d1d5db;
  border-radius: 10px;
  font-size: 1rem;
}
.actions {
  display: flex;
  gap: 12px;
}
.actions button {
  padding: 10px 18px;
  border: none;
  border-radius: 10px;
  background: #2563eb;
  color: white;
  cursor: pointer;
}
.table-panel table {
  width: 100%;
  border-collapse: collapse;
}
.table-panel th,
.table-panel td {
  padding: 12px 10px;
  border-bottom: 1px solid #e5e7eb;
  text-align: left;
}
.table-panel button {
  margin-right: 8px;
  padding: 6px 10px;
  border: none;
  border-radius: 8px;
  background: #1d4ed8;
  color: white;
  cursor: pointer;
}
.table-panel button:last-child {
  background: #b91c1c;
}
</style>
