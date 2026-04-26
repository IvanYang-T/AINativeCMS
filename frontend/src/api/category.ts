import { http } from './http';

export interface CategoryPayload {
  name: string;
  slug: string;
  parentId?: number;
  status?: 'ACTIVE' | 'DISABLED';
}

export const categoryApi = {
  list() {
    return http.get<CategoryPayload[]>('/categories');
  },
  get(id: number) {
    return http.get<CategoryPayload>(`/categories/${id}`);
  },
  create(payload: CategoryPayload) {
    return http.post<CategoryPayload>('/categories', payload);
  },
  update(id: number, payload: CategoryPayload) {
    return http.put<CategoryPayload>(`/categories/${id}`, payload);
  },
  remove(id: number) {
    return http.delete<void>(`/categories/${id}`);
  },
};
