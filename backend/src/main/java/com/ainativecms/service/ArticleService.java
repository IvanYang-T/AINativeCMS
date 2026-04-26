package com.ainativecms.service;

import com.ainativecms.entity.Article;
import com.ainativecms.exception.ResourceNotFoundException;
import com.ainativecms.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> listAll() {
        return articleRepository.findAll();
    }

    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found: " + id));
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public Article update(Long id, Article request) {
        Article article = getById(id);
        article.setTitle(request.getTitle());
        article.setSlug(request.getSlug());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary());
        article.setStatus(request.getStatus());
        article.setUpdatedAt(java.time.LocalDateTime.now());
        return articleRepository.save(article);
    }

    public void delete(Long id) {
        Article article = getById(id);
        articleRepository.delete(article);
    }
}
