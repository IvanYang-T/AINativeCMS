<template>
  <div class="page-shell">
    <h2>分类管理</h2>
    <div class="panel">
      <form @submit.prevent="saveCategory">
        <div class="field">
          <label>名称</label>
          <input v-model="form.name" required />
        </div>
        <div class="field">
          <label>Slug</label>
          <input v-model="form.slug" required />
        </div>
        <div class="field">
          <label>父级分类 ID</label>
          <input type="number" v-model.number="form.parentId" />
        </div>
        <div class="field actions">
          <button type="submit">{{ form.id ? '更新' : '创建' }}</button>
          <button type="button" @click="resetForm">清空</button>
        </div>
      </form>
    </div>

    <div class="panel table-panel">
      <h3>分类列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>Slug</th>
            <th>父级</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in categories" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.slug }}</td>
            <td>{{ item.parentId || '—' }}</td>
            <td>{{ item.status }}</td>
            <td>
              <button @click="editCategory(item)">编辑</button>
              <button @click="deleteCategory(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { categoryApi } from '../api/category';

type CategoryForm = {
  id?: number;
  name: string;
  slug: string;
  parentId?: number;
  status: 'ACTIVE' | 'DISABLED';
};

const categories = ref<Array<Record<string, any>>>([]);
const form = reactive<CategoryForm>({
  name: '',
  slug: '',
  parentId: undefined,
  status: 'ACTIVE',
});

const loadCategories = async () => {
  const response = await categoryApi.list();
  categories.value = response.data;
};

const resetForm = () => {
  form.id = undefined;
  form.name = '';
  form.slug = '';
  form.parentId = undefined;
  form.status = 'ACTIVE';
};

const saveCategory = async () => {
  if (form.id) {
    await categoryApi.update(form.id, form as any);
  } else {
    await categoryApi.create(form as any);
  }
  resetForm();
  await loadCategories();
};

const editCategory = (item: any) => {
  form.id = item.id;
  form.name = item.name;
  form.slug = item.slug;
  form.parentId = item.parentId;
  form.status = item.status || 'ACTIVE';
};

const deleteCategory = async (id: number) => {
  await categoryApi.remove(id);
  await loadCategories();
};

onMounted(loadCategories);
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
