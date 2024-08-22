package com.pranav.quizapp.service;

import com.pranav.quizapp.dao.QuestionDao;
import com.pranav.quizapp.dao.QuizDao;
import com.pranav.quizapp.model.Question;
import com.pranav.quizapp.model.QuestionWrapper;
import com.pranav.quizapp.model.Quiz;
import com.pranav.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int noOfQuestions, String title) {
        Quiz quiz=new Quiz();
        quiz.setTitle(title);

        List<Question> questions=questionDao.findRandomQuestionsByCategory(category, noOfQuestions);

        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz=quizDao.findById(id);

        List<Question> questionsFromDB=quiz.get().getQuestions();

        List<QuestionWrapper> questionsForUser=new ArrayList<>();

        for(Question q:questionsFromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());

            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        Quiz quiz=quizDao.findById(id).get();
        System.out.println("quiz : "+quiz);

        int right=0;

        List<Question> questions=quiz.getQuestions();

        int i=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
