package com.project.sms.controller;

import com.project.sms.model.Article;
import com.project.sms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/articles/")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @RequestMapping("/")
    public String getAll(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "articles/list";
    }
    @RequestMapping(value = "/create")
    public String createArticle(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "articles/create";
    }
    @PostMapping("/store")
    //bindingResult got errors
    public String storeArticle(@Valid  Article article, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "articles/create";
        }
        articleService.createArticle(article);
        return "redirect:/articles/";
    }
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable(value = "id") String id) {
        articleService.deleteArticle(id);
        return "redirect:/articles/";
    }
    @GetMapping("/update/{id}")
    public ModelAndView updateArticleView(@PathVariable(value = "id") String id, ModelAndView model) {
        Article article = articleService.findById(id);
        model.addObject("article", article);
        model.setViewName("articles/update");
        return model;
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(value = "id") String id,Article article) {
        articleService.updateArticle(id, article);
        return "redirect:/articles/";
    }

}
