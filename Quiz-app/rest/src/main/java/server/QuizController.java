package server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    @GetMapping("/")
    public void getAllQuiz () {
        System.out.println("Hei");
    }

    @GetMapping("/quizzes/{id}")
    public String getQuiz (@PathVariable("id") int id) {
        System.out.println(id);

        return "your quiz id: "+id;
    }


}
