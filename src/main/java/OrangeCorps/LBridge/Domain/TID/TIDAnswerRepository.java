package OrangeCorps.LBridge.Domain.TID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

// TIDAnswer entity에 의해 생성된 DB에 접근하는 메소드 사용을 위한 인터페이스.
@Repository
public interface TIDAnswerRepository extends JpaRepository<TIDAnswer, Long> {

    Optional<TIDAnswer> findByQuestionIdAndUserId(Long questionId, String userId);
}
