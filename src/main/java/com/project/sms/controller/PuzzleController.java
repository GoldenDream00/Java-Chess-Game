package com.project.sms.controller;

import com.project.sms.model.Article;
import com.project.sms.model.Puzzle;
import com.project.sms.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/puzzle/")
public class PuzzleController {

    @Autowired
    PuzzleService puzzleService;
    @GetMapping("/")
    public String puzzle(Model model) {
        List<Puzzle> puzzleList = puzzleService.findAll();
        model.addAttribute("puzzleList", puzzleList);
        return "puzzle/list";
    }
    @GetMapping("/create")
    public String createPuzzle(Model model) {
        Puzzle puzzle = new Puzzle();
        model.addAttribute("puzzle", puzzle);
        return "puzzle/create";
    }

    @PostMapping("/store")
    public String storePuzzle(Puzzle puzzle) {
        puzzleService.createPuzzle(puzzle);
        return "redirect:/puzzle/";
    }
    @GetMapping("/delete/{id}")
    public String deletePuzzle(@PathVariable(value = "id") String id) {
        puzzleService.deletePuzzle(id);
        return "redirect:/puzzle/";
    }
    @GetMapping("/update/{id}")
    public ModelAndView updatePuzzleView(@PathVariable(value = "id") String id, ModelAndView model) {
        Puzzle puzzle = puzzleService.findById(id);
        model.addObject("puzzle", puzzle);
        model.setViewName("puzzle/update");
        return model;
    }
    @PostMapping("/update/{id}")
    public String updatePuzzle(@PathVariable(value = "id") String id,Puzzle puzzle) {
        puzzleService.updatePuzzle(id, puzzle);
        return "redirect:/puzzle/";
    }
}
