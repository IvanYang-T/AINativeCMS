import { http } from './http';

export interface TemplatePayload {
  name: string;
  content: string;
  type?: string;
}

export const templateApi = {
  list() {
    return http.get<TemplatePayload[]>('/templates');
  },
  get(id: number) {
    return http.get<TemplatePayload>(`/templates/${id}`);
  },
  create(payload: TemplatePayload) {
    return http.post<TemplatePayload>('/templates', payload);
  },
  update(id: number, payload: TemplatePayload) {
    return http.put<TemplatePayload>(`/templates/${id}`, payload);
  },
  remove(id: number) {
    return http.delete<void>(`/templates/${id}`);
  },
};
