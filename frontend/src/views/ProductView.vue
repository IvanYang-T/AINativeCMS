<template>
  <div class="page-shell">
    <h2>产品管理</h2>
    <div class="panel">
      <form @submit.prevent="saveProduct">
        <div class="field">
          <label>名称</label>
          <input v-model="form.name" required />
        </div>
        <div class="field">
          <label>描述</label>
          <textarea v-model="form.description" rows="4" />
        </div>
        <div class="field">
          <label>价格</label>
          <input type="number" v-model.number="form.price" min="0" />
        </div>
        <div class="field">
          <label>图片 URL</label>
          <input v-model="form.imageUrl" />
        </div>
        <div class="field actions">
          <button type="submit">{{ form.id ? '更新' : '创建' }}</button>
          <button type="button" @click="resetForm">清空</button>
        </div>
      </form>
    </div>

    <div class="panel table-panel">
      <h3>产品列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>价格</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in products" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.price ?? '—' }}</td>
            <td>{{ item.status }}</td>
            <td>
              <button @click="editProduct(item)">编辑</button>
              <button @click="deleteProduct(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { productApi } from '../api/product';

type ProductForm = {
  id?: number;
  name: string;
  description: string;
  price?: number;
  imageUrl?: string;
  status: 'ACTIVE' | 'DISABLED' | 'OUT_OF_STOCK';
};

const products = ref<Array<Record<string, any>>>([]);
const form = reactive<ProductForm>({
  name: '',
  description: '',
  price: undefined,
  imageUrl: '',
  status: 'ACTIVE',
});

const loadProducts = async () => {
  const response = await productApi.list();
  products.value = response.data;
};

const resetForm = () => {
  form.id = undefined;
  form.name = '';
  form.description = '';
  form.price = undefined;
  form.imageUrl = '';
  form.status = 'ACTIVE';
};

const saveProduct = async () => {
  if (form.id) {
    await productApi.update(form.id, form as any);
  } else {
    await productApi.create(form as any);
  }
  resetForm();
  await loadProducts();
};

const editProduct = (item: any) => {
  form.id = item.id;
  form.name = item.name;
  form.description = item.description || '';
  form.price = item.price;
  form.imageUrl = item.imageUrl || '';
  form.status = item.status || 'ACTIVE';
};

const deleteProduct = async (id: number) => {
  await productApi.remove(id);
  await loadProducts();
};

onMounted(loadProducts);
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
