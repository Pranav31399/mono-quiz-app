package com.pranav.quizapp.controller;

import com.pranav.quizapp.model.Question;
import com.pranav.quizapp.model.QuestionWrapper;
import com.pranav.quizapp.model.Response;
import com.pranav.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int noOfQuestions, @RequestParam String title){
        return quizService.createQuiz(category,noOfQuestions,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}
