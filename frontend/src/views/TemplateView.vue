<template>
  <div class="page-shell">
    <h2>模板管理</h2>
    <div class="panel">
      <form @submit.prevent="saveTemplate">
        <div class="field">
          <label>名称</label>
          <input v-model="form.name" required />
        </div>
        <div class="field">
          <label>类型</label>
          <input v-model="form.type" />
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
      <h3>模板列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>类型</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in templates" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.type }}</td>
            <td>
              <button @click="editTemplate(item)">编辑</button>
              <button @click="deleteTemplate(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { templateApi } from '../api/template';

type TemplateForm = {
  id?: number;
  name: string;
  type?: string;
  content: string;
};

const templates = ref<Array<Record<string, any>>>([]);
const form = reactive<TemplateForm>({
  name: '',
  type: '',
  content: '',
});

const loadTemplates = async () => {
  const response = await templateApi.list();
  templates.value = response.data;
};

const resetForm = () => {
  form.id = undefined;
  form.name = '';
  form.type = '';
  form.content = '';
};

const saveTemplate = async () => {
  if (form.id) {
    await templateApi.update(form.id, form as any);
  } else {
    await templateApi.create(form as any);
  }
  resetForm();
  await loadTemplates();
};

const editTemplate = (item: any) => {
  form.id = item.id;
  form.name = item.name;
  form.type = item.type || '';
  form.content = item.content || '';
};

const deleteTemplate = async (id: number) => {
  await templateApi.remove(id);
  await loadTemplates();
};

onMounted(loadTemplates);
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
