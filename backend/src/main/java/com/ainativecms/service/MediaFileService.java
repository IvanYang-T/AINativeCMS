package com.ainativecms.service;

import com.ainativecms.entity.MediaFile;
import com.ainativecms.exception.ResourceNotFoundException;
import com.ainativecms.repository.MediaFileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    public List<MediaFile> listAll() {
        return mediaFileRepository.findAll();
    }

    public MediaFile getById(Long id) {
        return mediaFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media file not found: " + id));
    }

    public MediaFile create(MediaFile mediaFile) {
        return mediaFileRepository.save(mediaFile);
    }

    public MediaFile update(Long id, MediaFile request) {
        MediaFile mediaFile = getById(id);
        mediaFile.setFileName(request.getFileName());
        mediaFile.setUrl(request.getUrl());
        mediaFile.setMimeType(request.getMimeType());
        mediaFile.setSize(request.getSize());
        mediaFile.setStatus(request.getStatus());
        mediaFile.setUpdatedAt(LocalDateTime.now());
        return mediaFileRepository.save(mediaFile);
    }

    public void delete(Long id) {
        MediaFile mediaFile = getById(id);
        mediaFileRepository.delete(mediaFile);
    }
}
