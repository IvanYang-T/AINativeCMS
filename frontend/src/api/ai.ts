import { http } from './http';

export interface AiSummaryPayload {
  title: string;
  content: string;
  maxLength?: number;
}

export interface AiTextResult {
  text: string;
}

export const aiApi = {
  summary(payload: AiSummaryPayload) {
    return http.post<AiTextResult>('/ai/editor/summary', payload);
  },
};
