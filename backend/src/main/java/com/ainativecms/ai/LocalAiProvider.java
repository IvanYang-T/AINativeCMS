package com.ainativecms.ai;

import com.ainativecms.entity.AiConfig;
import org.springframework.stereotype.Component;

@Component
public class LocalAiProvider implements AiProvider {

    @Override
    public AiConfig.AiProviderType getType() {
        return AiConfig.AiProviderType.LOCAL;
    }

    @Override
    public AiResponse chat(AiConfig config, AiRequest request) {
        String prompt = request.getUserPrompt() == null ? "" : request.getUserPrompt();
        String result = generateText(prompt);
        AiResponse response = new AiResponse(result);
        response.setModel(config == null || config.getModel() == null ? "local-model" : config.getModel());
        response.setPromptTokens(prompt.length() / 4);
        response.setCompletionTokens(Math.max(1, result.length() / 4));
        return response;
    }

    private String generateText(String prompt) {
        if (prompt.contains("rewrite")) {
            return "[AI] 改写结果：" + prompt.replaceAll("(?i)rewrite|改写", "");
        }
        if (prompt.contains("summary")) {
            return "[AI] 摘要：" + truncate(prompt.replaceAll("(?i)summary|摘要", ""), 120);
        }
        if (prompt.contains("title")) {
            return "[AI] 标题建议：" + truncate(prompt.replaceAll("(?i)title|标题", ""), 60);
        }
        return "[AI] 生成内容：" + truncate(prompt, 200);
    }

    private String truncate(String text, int maxLength) {
        String clean = text.trim();
        if (clean.length() <= maxLength) {
            return clean;
        }
        return clean.substring(0, maxLength) + "...";
    }
}
