package OrangeCorps.LBridge.Controller;

import OrangeCorps.LBridge.Domain.TID.TIDAnswer;
import OrangeCorps.LBridge.Domain.TID.TIDAnswerRepository;
import OrangeCorps.LBridge.Domain.TID.TIDQuestion;
import OrangeCorps.LBridge.Domain.TID.TIDQuestionRepository;
import OrangeCorps.LBridge.Domain.User.UserRepository;
import OrangeCorps.LBridge.Service.TID.TIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tid")
public class TIDController { // 실제 uri 작성하고 필요한 함수들 넣기

    private UserRepository userRepository;
    private TIDQuestionRepository tidQuestionRepository;
    private TIDAnswerRepository tidAnswerRepository;

    @Autowired
    private TIDService tidService;

    // TID 질문 만들기
    @PostMapping("/questions/new")
    public ResponseEntity<TIDQuestion> createNewQuestion(
            @RequestBody String question
    ) {
            TIDQuestion newQuestion = tidService.createNewTIDQuestion(question);
            return new ResponseEntity<>(newQuestion, HttpStatus.OK);
    }

    // TID 질문 불러오기
    @GetMapping("/questions")
    public ResponseEntity<TIDQuestion> getQuestion() {
        TIDQuestion response = tidService.getRandomQuestion();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // TID 답안 제출 (조회로 바로 넘어가야 한다? -> TID 답안 제출 + TID 답안 조회 같이 호출)
    @PostMapping("/answers/{questionId}/{userId}")
    public ResponseEntity<TIDAnswer> answerQuestion(
            @PathVariable Long questionId,
            @PathVariable String userId,
            @RequestBody String answer
    ) {
        TIDAnswer response = tidService.saveAnswerForQuestion(questionId, userId, answer);
        return ResponseEntity.ok(response);
    }

    // TID 답안 조회 (상대 답변 존재할 경우)
    @GetMapping("/answers/{questionId}/{userId}")
    public ResponseEntity<String> getAnswer(
            @PathVariable Long questionId,
            @PathVariable String userId
    ) {
        // 상대가 답변했는지 확인
        if (tidService.coupleAnswerExist(questionId, userId)) {
            String response = tidService.getCoupleAnswerForQuestion(questionId, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Error: Couple answer not found.", HttpStatus.NOT_FOUND);
        }
    }
}
