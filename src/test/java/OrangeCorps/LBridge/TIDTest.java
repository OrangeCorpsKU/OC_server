package OrangeCorps.LBridge;

import OrangeCorps.LBridge.Domain.TID.TIDAnswer;
import OrangeCorps.LBridge.Domain.TID.TIDAnswerRepository;
import OrangeCorps.LBridge.Domain.TID.TIDQuestion;
import OrangeCorps.LBridge.Domain.TID.TIDQuestionRepository;
import OrangeCorps.LBridge.Service.TID.TIDService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@SpringBootTest
@Transactional
public class TIDTest {
    @Autowired
    private TIDAnswerRepository tidAnswerRepository;

    @Autowired
    private TIDQuestionRepository tidQuestionRepository;

    @Autowired TIDService tidService;


    @Test
    @DisplayName("TID 질문 생성되는지 테스트")
    void testCreateNewTIDQuestion() {
        TIDQuestion newQuestion = tidService.createNewTIDQuestion("test question");
        String question = newQuestion.getQuestion();
        assertEquals("test question", question);
    }

    @Test
    @DisplayName("TID 질문 랜덤으로 불러와지는지 테스트")
    void testGetRandomQuestion() {
        TIDQuestion newQuestion1 = tidService.createNewTIDQuestion("test question1");
        Long id1 = newQuestion1.getQuestionId();
        TIDQuestion newQuestion2 = tidService.createNewTIDQuestion("test question2");
        Long id2 = newQuestion2.getQuestionId();
        TIDQuestion newQuestion3 = tidService.createNewTIDQuestion("test question3");
        Long id3 = newQuestion3.getQuestionId();

        TIDQuestion getRandom = tidService.getRandomQuestion();
        Long idRandom = getRandom.getQuestionId();
        assertThat(List.of(id1, id2, id3)).contains(idRandom);
    }

    @Test
    @DisplayName("TID 대답 저장되는지 테스트")
    void testSaveAnswerForQuestion() {

        TIDQuestion newQuestion = tidService.createNewTIDQuestion("test question");

        TIDAnswer newAnswer = new TIDAnswer();
        newAnswer.setQuestionId(newQuestion.getQuestionId());
        newAnswer.setAnswer("test answer");
        newAnswer.setUserId("testuser");
        newAnswer.setCoupleId("testcouple");

        TIDAnswer savedAnswer = tidAnswerRepository.save(newAnswer);

        assertEquals(newAnswer.getQuestionId(), savedAnswer.getQuestionId());
        assertEquals(newAnswer.getUserId(), savedAnswer.getUserId());
        assertEquals(newAnswer.getCoupleId(), savedAnswer.getCoupleId());
        assertEquals(newAnswer.getAnswer(), savedAnswer.getAnswer());
    }


    @Test
    @DisplayName("TID 상대 대답 존재o일때 조회 테스트")
    void testGetCoupleAnswerForQuestion1() {
        TIDQuestion newQuestion = tidService.createNewTIDQuestion("test question");

        TIDAnswer newAnswer1 = new TIDAnswer();
        newAnswer1.setQuestionId(newQuestion.getQuestionId());
        newAnswer1.setAnswer("test user answer");
        newAnswer1.setUserId("대답한 본인유저");
        newAnswer1.setCoupleId("대답안한 커플유저");

        tidAnswerRepository.save(newAnswer1);


        Optional<TIDAnswer> optionalTIDAnswer = tidAnswerRepository.findByQuestionIdAndUserId(newQuestion.getQuestionId(), newAnswer1.getCoupleId());
        boolean doesExist = optionalTIDAnswer.isPresent();
        assertFalse(doesExist, "상대방이 질문에 대답하지 않아야 합니다.");
    }

    @Test
    @DisplayName("TID 상대 대답 존재o일때 조회 테스트")
    void testGetCoupleAnswerForQuestion2() {
        TIDQuestion newQuestion = tidService.createNewTIDQuestion("test question");

        TIDAnswer newAnswer1 = new TIDAnswer();
        newAnswer1.setQuestionId(newQuestion.getQuestionId());
        newAnswer1.setAnswer("test user answer");
        newAnswer1.setUserId("대답한 본인유저");
        newAnswer1.setCoupleId("대답한 커플유저");

        tidAnswerRepository.save(newAnswer1);

        TIDAnswer newAnswer2 = new TIDAnswer();
        newAnswer2.setQuestionId(newQuestion.getQuestionId());
        newAnswer2.setAnswer("test couple answer");
        newAnswer2.setUserId("대답한 커플유저");
        newAnswer2.setCoupleId("대답한 본인유저");

        tidAnswerRepository.save(newAnswer2);


        Optional<TIDAnswer> optionalTIDAnswer = tidAnswerRepository.findByQuestionIdAndUserId(newQuestion.getQuestionId(), newAnswer1.getCoupleId());
        if (optionalTIDAnswer.isPresent()) {
            TIDAnswer tidAnswer = optionalTIDAnswer.get();
            String answer = tidAnswer.getAnswer();
            assertEquals("test couple answer", answer, "상대방의 답변이 존재합니다.");
        }
    }
}
