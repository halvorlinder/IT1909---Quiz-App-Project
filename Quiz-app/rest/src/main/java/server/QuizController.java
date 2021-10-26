package server;

import core.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

    @GetMapping("/")
    public void getAllQuiz () {
        System.out.println("Hei");
    }

    @GetMapping("/q")
    public Question getRandomQ () {
        return new Question("question", List.of("1", "2", "3", "4"), 0);
    }


}
