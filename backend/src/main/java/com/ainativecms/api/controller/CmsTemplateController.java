package com.ainativecms.api.controller;

import com.ainativecms.entity.CmsTemplate;
import com.ainativecms.service.CmsTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class CmsTemplateController {

    private final CmsTemplateService templateService;

    public CmsTemplateController(CmsTemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<List<CmsTemplate>> list() {
        return ResponseEntity.ok(templateService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CmsTemplate> get(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CmsTemplate> create(@Valid @RequestBody CmsTemplate template) {
        return ResponseEntity.ok(templateService.create(template));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CmsTemplate> update(@PathVariable Long id, @Valid @RequestBody CmsTemplate template) {
        return ResponseEntity.ok(templateService.update(id, template));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
