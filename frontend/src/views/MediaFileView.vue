<template>
  <div class="page-shell">
    <h2>媒体文件管理</h2>
    <div class="panel">
      <form @submit.prevent="saveMediaFile">
        <div class="field">
          <label>文件名</label>
          <input v-model="form.fileName" required />
        </div>
        <div class="field">
          <label>URL</label>
          <input v-model="form.url" required />
        </div>
        <div class="field">
          <label>Mime 类型</label>
          <input v-model="form.mimeType" />
        </div>
        <div class="field">
          <label>大小 (bytes)</label>
          <input type="number" v-model.number="form.size" min="0" />
        </div>
        <div class="field actions">
          <button type="submit">{{ form.id ? '更新' : '创建' }}</button>
          <button type="button" @click="resetForm">清空</button>
        </div>
      </form>
    </div>

    <div class="panel table-panel">
      <h3>媒体文件列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>文件名</th>
            <th>URL</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in mediaFiles" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.fileName }}</td>
            <td><a :href="item.url" target="_blank">查看</a></td>
            <td>{{ item.status }}</td>
            <td>
              <button @click="editMediaFile(item)">编辑</button>
              <button @click="deleteMediaFile(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { mediaFileApi } from '../api/mediaFile';

type MediaFileForm = {
  id?: number;
  fileName: string;
  url: string;
  mimeType?: string;
  size?: number;
  status: 'AVAILABLE' | 'ARCHIVED';
};

const mediaFiles = ref<Array<Record<string, any>>>([]);
const form = reactive<MediaFileForm>({
  fileName: '',
  url: '',
  mimeType: '',
  size: undefined,
  status: 'AVAILABLE',
});

const loadMediaFiles = async () => {
  const response = await mediaFileApi.list();
  mediaFiles.value = response.data;
};

const resetForm = () => {
  form.id = undefined;
  form.fileName = '';
  form.url = '';
  form.mimeType = '';
  form.size = undefined;
  form.status = 'AVAILABLE';
};

const saveMediaFile = async () => {
  if (form.id) {
    await mediaFileApi.update(form.id, form as any);
  } else {
    await mediaFileApi.create(form as any);
  }
  resetForm();
  await loadMediaFiles();
};

const editMediaFile = (item: any) => {
  form.id = item.id;
  form.fileName = item.fileName;
  form.url = item.url;
  form.mimeType = item.mimeType || '';
  form.size = item.size;
  form.status = item.status || 'AVAILABLE';
};

const deleteMediaFile = async (id: number) => {
  await mediaFileApi.remove(id);
  await loadMediaFiles();
};

onMounted(loadMediaFiles);
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
.field input {
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
