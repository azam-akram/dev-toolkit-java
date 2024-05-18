package test.service.discovery.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.service.discovery.message.model.entity.MessageEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    Optional<MessageEntity> findByMessageKey(String messageKey);

    List<MessageEntity> findTop2ByOrderByIdDesc();

    Optional<MessageEntity> findByMessageKeyAndSavedAtAfter(String messageKey, LocalDateTime after);

    @Query(value = "SELECT * FROM message order by id desc limit :row_limit", nativeQuery = true)
    List<MessageEntity> findLastNEmployees(@Param("row_limit") Integer row_limit);

    void deleteBySavedAtBefore(LocalDateTime since);
}
