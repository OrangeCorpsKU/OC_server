package OrangeCorps.LBridge.TIDTest;

import OrangeCorps.LBridge.Domain.TID.TIDAnswerRepository;
import OrangeCorps.LBridge.Domain.TID.TIDQuestion;
import OrangeCorps.LBridge.Domain.TID.TIDQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TIDRepositoryTest {
    @Autowired
    private TIDAnswerRepository tidAnswerRepository;

    @Autowired
    private TIDQuestionRepository tidQuestionRepository;

    // TID 질문 불러오기 test
    @Test
    @DisplayName("TID 질문 제대로 불러와지는지 테스트")
    void testGetTIDQuestions() {
        TIDQuestion tidQuestion = new TIDQuestion();
        tidQuestion.setQuestion();

    }

    // TID 답안제출 test
    @Test
    @DisplayName("TID 답안제출 정상적으로 올라가는지 테스트")
    void testPostTIDAnswer() {

    }

    // TID 답안 확인 test
    @Test
    @DisplayName("TID 답안 확인 정상적으로 조회되는지 테스트")
    void testGetTIDAnswer() {

    }
}
