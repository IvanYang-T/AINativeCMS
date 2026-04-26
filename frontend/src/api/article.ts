import { http } from './http';

export interface ArticlePayload {
  title: string;
  slug: string;
  content: string;
  summary?: string;
  status?: 'DRAFT' | 'PUBLISHED' | 'ARCHIVED';
}

export const articleApi = {
  list() {
    return http.get<ArticlePayload[]>('/articles');
  },
  get(id: number) {
    return http.get<ArticlePayload>(`/articles/${id}`);
  },
  create(payload: ArticlePayload) {
    return http.post<ArticlePayload>('/articles', payload);
  },
  update(id: number, payload: ArticlePayload) {
    return http.put<ArticlePayload>(`/articles/${id}`, payload);
  },
  remove(id: number) {
    return http.delete<void>(`/articles/${id}`);
  },
};
