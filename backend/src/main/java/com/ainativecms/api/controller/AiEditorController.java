package com.ainativecms.api.controller;

import com.ainativecms.ai.AiProviderFactory;
import com.ainativecms.ai.AiRequest;
import com.ainativecms.ai.AiResponse;
import com.ainativecms.dto.AiSeoMetaRequest;
import com.ainativecms.dto.AiSeoMetaResponse;
import com.ainativecms.dto.AiSummaryRequest;
import com.ainativecms.dto.AiTextResult;
import com.ainativecms.dto.AiTitlesRequest;
import com.ainativecms.dto.AiRewriteRequest;
import com.ainativecms.entity.AiConfig;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai/editor")
public class AiEditorController {

    private final AiProviderFactory aiProviderFactory;

    public AiEditorController(AiProviderFactory aiProviderFactory) {
        this.aiProviderFactory = aiProviderFactory;
    }

    @PostMapping("/summary")
    public ResponseEntity<AiTextResult> summary(@Valid @RequestBody AiSummaryRequest request) throws Exception {
        AiRequest aiRequest = new AiRequest("请作为文本摘要助手。", "summary: " + request.getTitle() + "\n" + request.getContent());
        aiRequest.setMaxTokens(request.getMaxLength() != null ? request.getMaxLength() : 120);
        AiResponse result = aiProviderFactory.get(defaultConfig().getProvider()).chat(defaultConfig(), aiRequest);
        return ResponseEntity.ok(new AiTextResult(result.getContent()));
    }

    @PostMapping("/rewrite")
    public ResponseEntity<AiTextResult> rewrite(@Valid @RequestBody AiRewriteRequest request) throws Exception {
        String prompt = "rewrite style=" + (request.getStyle() == null ? "FORMAL" : request.getStyle()) + ": " + request.getText();
        AiRequest aiRequest = new AiRequest("请改写以下文本。", prompt);
        AiResponse result = aiProviderFactory.get(defaultConfig().getProvider()).chat(defaultConfig(), aiRequest);
        return ResponseEntity.ok(new AiTextResult(result.getContent()));
    }

    @PostMapping("/titles")
    public ResponseEntity<List<String>> titles(@Valid @RequestBody AiTitlesRequest request) throws Exception {
        int count = request.getCount() == null || request.getCount() < 1 ? 3 : request.getCount();
        AiRequest aiRequest = new AiRequest("请为以下内容生成标题建议。", "titles: " + request.getContent() + "\ncount=" + count);
        AiResponse result = aiProviderFactory.get(defaultConfig().getProvider()).chat(defaultConfig(), aiRequest);
        List<String> candidates = generateTitles(result.getContent(), count);
        return ResponseEntity.ok(candidates);
    }

    @PostMapping("/seo-meta")
    public ResponseEntity<AiSeoMetaResponse> seoMeta(@Valid @RequestBody AiSeoMetaRequest request) throws Exception {
        AiRequest aiRequest = new AiRequest("请为以下内容生成 SEO 元数据。", "title: " + request.getTitle() + "\ncontent: " + request.getContent());
        AiResponse result = aiProviderFactory.get(defaultConfig().getProvider()).chat(defaultConfig(), aiRequest);
        String generated = result.getContent();
        return ResponseEntity.ok(parseSeoMeta(generated, request.getTitle()));
    }

    private AiConfig defaultConfig() {
        AiConfig config = new AiConfig();
        config.setProvider(AiConfig.AiProviderType.LOCAL);
        config.setModel("local-model");
        return config;
    }

    private List<String> generateTitles(String content, int count) {
        return List.of(content).stream()
                .flatMap(text -> List.of(text.split(";|，|。|\n")).stream())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .limit(count)
                .collect(Collectors.toList());
    }

    private AiSeoMetaResponse parseSeoMeta(String generated, String title) {
        String description = generated.length() > 120 ? generated.substring(0, 120) : generated;
        String keywords = title == null ? "" : title.replaceAll("\\s+", ", ");
        return new AiSeoMetaResponse(title, description, keywords);
    }
}
