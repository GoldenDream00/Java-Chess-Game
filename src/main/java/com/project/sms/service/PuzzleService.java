package com.project.sms.service;

import com.project.sms.model.Article;
import com.project.sms.model.Puzzle;
import com.project.sms.repository.ArticleRepository;
import com.project.sms.repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PuzzleService {
    @Autowired
    PuzzleRepository puzzleRepository;

    public void createPuzzle(Puzzle puzzle) {
        puzzleRepository.save(puzzle);
    }

    public List<Puzzle> findAll() {
        return puzzleRepository.findAll();
    }
    public boolean updatePuzzle(String id, Puzzle puzzle) {
        Puzzle previousPuzzle = puzzleRepository.findById(id).get();
        puzzle.setId(previousPuzzle.getId());
        puzzle.setDataPublished(previousPuzzle.getDataPublished());
        puzzleRepository.save(puzzle);
        return true;
    }
    public void deletePuzzle(String id) {
        puzzleRepository.deleteById(id);
    }
    public Puzzle findById(String id) {
        Optional<Puzzle> articleOptional =  puzzleRepository.findById(id);
        return articleOptional.get();
    }

    public List<Puzzle> findAllByIDs(List<String> ids) {
        Iterable<Puzzle> puzzles = puzzleRepository.findAllById(ids);
        return StreamSupport.stream(puzzles.spliterator(), false).collect(Collectors.toList());
    }
}
