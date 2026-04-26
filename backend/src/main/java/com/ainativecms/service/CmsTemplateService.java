package com.ainativecms.service;

import com.ainativecms.entity.CmsTemplate;
import com.ainativecms.exception.ResourceNotFoundException;
import com.ainativecms.repository.CmsTemplateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CmsTemplateService {

    private final CmsTemplateRepository templateRepository;

    public CmsTemplateService(CmsTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<CmsTemplate> listAll() {
        return templateRepository.findAll();
    }

    public CmsTemplate getById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found: " + id));
    }

    public CmsTemplate create(CmsTemplate template) {
        return templateRepository.save(template);
    }

    public CmsTemplate update(Long id, CmsTemplate request) {
        CmsTemplate template = getById(id);
        template.setName(request.getName());
        template.setType(request.getType());
        template.setContent(request.getContent());
        template.setUpdatedAt(LocalDateTime.now());
        return templateRepository.save(template);
    }

    public void delete(Long id) {
        CmsTemplate template = getById(id);
        templateRepository.delete(template);
    }
}
