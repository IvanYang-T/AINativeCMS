package com.ainativecms.service;

import com.ainativecms.entity.Page;
import com.ainativecms.exception.ResourceNotFoundException;
import com.ainativecms.repository.PageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public List<Page> listAll() {
        return pageRepository.findAll();
    }

    public Page getById(Long id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found: " + id));
    }

    public Page create(Page page) {
        return pageRepository.save(page);
    }

    public Page update(Long id, Page request) {
        Page page = getById(id);
        page.setTitle(request.getTitle());
        page.setUrlPath(request.getUrlPath());
        page.setContent(request.getContent());
        page.setStatus(request.getStatus());
        page.setUpdatedAt(java.time.LocalDateTime.now());
        return pageRepository.save(page);
    }

    public void delete(Long id) {
        Page page = getById(id);
        pageRepository.delete(page);
    }
}
