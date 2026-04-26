import { http } from './http';

export interface PagePayload {
  title: string;
  urlPath: string;
  content: string;
  status?: 'DRAFT' | 'PUBLISHED';
}

export const pageApi = {
  list() {
    return http.get<PagePayload[]>('/pages');
  },
  get(id: number) {
    return http.get<PagePayload>(`/pages/${id}`);
  },
  create(payload: PagePayload) {
    return http.post<PagePayload>('/pages', payload);
  },
  update(id: number, payload: PagePayload) {
    return http.put<PagePayload>(`/pages/${id}`, payload);
  },
  remove(id: number) {
    return http.delete<void>(`/pages/${id}`);
  },
};
