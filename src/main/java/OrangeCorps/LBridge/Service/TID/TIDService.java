package OrangeCorps.LBridge.Service.TID;

import OrangeCorps.LBridge.Domain.TID.TIDAnswer;
import OrangeCorps.LBridge.Domain.TID.TIDAnswerRepository;
import OrangeCorps.LBridge.Domain.TID.TIDQuestion;
import OrangeCorps.LBridge.Domain.TID.TIDQuestionRepository;
import OrangeCorps.LBridge.Domain.User.User;
import OrangeCorps.LBridge.Domain.User.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TIDService { // 필요한 함수들 구현
    private UserRepository userRepository;
    @Autowired
    private TIDAnswerRepository tidAnswerRepository;
    @Autowired
    private TIDQuestionRepository tidQuestionRepository;

    @Transactional
    public TIDQuestion createNewTIDQuestion(String question) { // 새로운 TID 질문 생성
        // 새로운 질문 생성 및 저장
        TIDQuestion newQuestion = new TIDQuestion();
        newQuestion.setQuestion(question);
        tidQuestionRepository.save(newQuestion);

        return newQuestion;
    }

    @Transactional
    public TIDQuestion getRandomQuestion() { // db에서 랜덤하게 질문 가져오기
        try {
            return tidQuestionRepository.findRandomTIDQuestion();
        } catch (NoResultException e) {
            // db에서 질문을 찾을 수 없을 때의 예외 처리
            return null;
        }

    }

    @Transactional
    public boolean coupleAnswerExist(Long questionId, String userId) { // 질문에 대한 커플 응답 존재여부 확
        Optional<User> optionalUser = userRepository.findByUuid(userId);
        User user = optionalUser.get();
        String coupleId = user.getCoupleId();

        Optional<TIDAnswer> optionalTIDAnswer = tidAnswerRepository.findByQuestionIdAndUserId(questionId, coupleId);
        TIDAnswer tidAnswer = optionalTIDAnswer.get();
        return Objects.nonNull(tidAnswer);
    }

    @Transactional
    public String getCoupleAnswerForQuestion(Long questionId, String userId) {

        Optional<User> optionalUser = userRepository.findByUuid(userId);
        User user = optionalUser.get();
        String coupleId = user.getCoupleId();

        // 불러오는거 추가하기
        Optional<TIDAnswer> optionalTIDAnswer = tidAnswerRepository.findByQuestionIdAndUserId(questionId, coupleId);
        TIDAnswer tidAnswer = optionalTIDAnswer.get();
        String userAnswer = tidAnswer.getAnswer();
        return userAnswer;
    }

    @Transactional
    public TIDAnswer saveAnswerForQuestion(Long questionId, String userId, String userAnswer) {

        // 질문에 대한 답변 저장
        TIDAnswer newAnswer = new TIDAnswer();
        newAnswer.setQuestionId(questionId);
        newAnswer.setAnswer(userAnswer);
        newAnswer.setUserId(userId);

        Optional<User> optionalUser = userRepository.findByUuid(userId);
        User user = optionalUser.get();
        String coupleId = user.getCoupleId();
        newAnswer.setCoupleId(coupleId);

        TIDAnswer savedAnswer = tidAnswerRepository.save(newAnswer);

        return savedAnswer;
    }
}