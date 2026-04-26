import { http } from './http';

export interface ProductPayload {
  name: string;
  description: string;
  price?: number;
  imageUrl?: string;
  status?: 'ACTIVE' | 'DISABLED' | 'OUT_OF_STOCK';
}

export const productApi = {
  list() {
    return http.get<ProductPayload[]>('/products');
  },
  get(id: number) {
    return http.get<ProductPayload>(`/products/${id}`);
  },
  create(payload: ProductPayload) {
    return http.post<ProductPayload>('/products', payload);
  },
  update(id: number, payload: ProductPayload) {
    return http.put<ProductPayload>(`/products/${id}`, payload);
  },
  remove(id: number) {
    return http.delete<void>(`/products/${id}`);
  },
};
