package OrangeCorps.LBridge.Domain.TID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TIDQuestionRepository extends JpaRepository<TIDQuestion, Long> {
    Optional<TIDQuestion> findByQuestionId(Long questionId);

    // 랜덤으로 하나의 TIDQuestion 객체를 가져오는 쿼리
    @Query(value = "SELECT * FROM tid_question ORDER BY RAND() LIMIT 1", nativeQuery = true)
    TIDQuestion findRandomTIDQuestion();

}
