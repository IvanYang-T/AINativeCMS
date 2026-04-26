package com.ainativecms.api.controller;

import com.ainativecms.entity.Page;
import com.ainativecms.service.PageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<Page>> list() {
        return ResponseEntity.ok(pageService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page> get(@PathVariable Long id) {
        return ResponseEntity.ok(pageService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Page> create(@Valid @RequestBody Page page) {
        return ResponseEntity.ok(pageService.create(page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Page> update(@PathVariable Long id, @Valid @RequestBody Page page) {
        return ResponseEntity.ok(pageService.update(id, page));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
