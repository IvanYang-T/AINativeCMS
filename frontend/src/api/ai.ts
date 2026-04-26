import { http } from './http';

export interface AiSummaryPayload {
  title: string;
  content: string;
  maxLength?: number;
}

export interface AiRewritePayload {
  text: string;
  style?: 'FORMAL' | 'CASUAL';
}

export interface AiTitlesPayload {
  content: string;
  count?: number;
}

export interface AiSeoMetaPayload {
  title: string;
  content: string;
}

export interface AiTextResult {
  text: string;
}

export interface AiSeoMetaResult {
  title: string;
  description: string;
  keywords: string;
}

export const aiApi = {
  summary(payload: AiSummaryPayload) {
    return http.post<AiTextResult>('/ai/editor/summary', payload);
  },
  rewrite(payload: AiRewritePayload) {
    return http.post<AiTextResult>('/ai/editor/rewrite', payload);
  },
  titles(payload: AiTitlesPayload) {
    return http.post<string[]>('/ai/editor/titles', payload);
  },
  seoMeta(payload: AiSeoMetaPayload) {
    return http.post<AiSeoMetaResult>('/ai/editor/seo-meta', payload);
  },
};
