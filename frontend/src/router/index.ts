import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import ArticleView from '../views/ArticleView.vue';
import PageView from '../views/PageView.vue';
import ProductView from '../views/ProductView.vue';
import CategoryView from '../views/CategoryView.vue';
import TemplateView from '../views/TemplateView.vue';
import MediaFileView from '../views/MediaFileView.vue';
import AiEditorView from '../views/AiEditorView.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path: '/articles',
    name: 'Articles',
    component: ArticleView,
  },
  {
    path: '/pages',
    name: 'Pages',
    component: PageView,
  },
  {
    path: '/products',
    name: 'Products',
    component: ProductView,
  },
  {
    path: '/categories',
    name: 'Categories',
    component: CategoryView,
  },
  {
    path: '/templates',
    name: 'Templates',
    component: TemplateView,
  },
  {
    path: '/media-files',
    name: 'MediaFiles',
    component: MediaFileView,
  },
  {
    path: '/ai-editor',
    name: 'AiEditor',
    component: AiEditorView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
