package com.project.sms.repository;

import com.project.sms.model.Puzzle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuzzleRepository extends MongoRepository<Puzzle, String> {
//    @Override
//    List<Puzzle> findAllById(List<String> ids);
}
