package com.project.sms.service;

import com.project.sms.model.Article;
import com.project.sms.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {

        return articleRepository.findAll();
    }
    public boolean updateArticle(String id, Article article) {
        Article previousArticle = articleRepository.findById(id).get();
        article.setId(previousArticle.getId());
        article.setDataPublished(previousArticle.getDataPublished());
        articleRepository.save(article);
        return true;
    }
    public void deleteArticle(String id) {
        articleRepository.deleteById(id);
    }
    public Article findById(String id) {
        Optional<Article> articleOptional =  articleRepository.findById(id);
        return articleOptional.get();
    }
}
