import { http } from './http';

export interface MediaFilePayload {
  fileName: string;
  url: string;
  mimeType?: string;
  size?: number;
  status?: 'AVAILABLE' | 'ARCHIVED';
}

export const mediaFileApi = {
  list() {
    return http.get<MediaFilePayload[]>('/media-files');
  },
  get(id: number) {
    return http.get<MediaFilePayload>(`/media-files/${id}`);
  },
  create(payload: MediaFilePayload) {
    return http.post<MediaFilePayload>('/media-files', payload);
  },
  update(id: number, payload: MediaFilePayload) {
    return http.put<MediaFilePayload>(`/media-files/${id}`, payload);
  },
  remove(id: number) {
    return http.delete<void>(`/media-files/${id}`);
  },
};
