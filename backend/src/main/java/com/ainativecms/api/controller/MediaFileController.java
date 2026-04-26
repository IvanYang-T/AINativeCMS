package com.ainativecms.api.controller;

import com.ainativecms.entity.MediaFile;
import com.ainativecms.service.MediaFileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media-files")
public class MediaFileController {

    private final MediaFileService mediaFileService;

    public MediaFileController(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    @GetMapping
    public ResponseEntity<List<MediaFile>> list() {
        return ResponseEntity.ok(mediaFileService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaFile> get(@PathVariable Long id) {
        return ResponseEntity.ok(mediaFileService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MediaFile> create(@Valid @RequestBody MediaFile mediaFile) {
        return ResponseEntity.ok(mediaFileService.create(mediaFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaFile> update(@PathVariable Long id, @Valid @RequestBody MediaFile mediaFile) {
        return ResponseEntity.ok(mediaFileService.update(id, mediaFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mediaFileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
