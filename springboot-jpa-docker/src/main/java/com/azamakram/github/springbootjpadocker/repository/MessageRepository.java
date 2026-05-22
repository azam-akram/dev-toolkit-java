package com.azamakram.github.springbootjpadocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.azamakram.github.springbootjpadocker.model.entity.MessageEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    Optional<MessageEntity> findByMessageKey(String messageKey);

    Optional<MessageEntity> findByMessageKeyAndSavedAtAfter(String messageKey, LocalDateTime after);

    @Query(value = "SELECT * FROM message ORDER BY id DESC LIMIT :row_limit", nativeQuery = true)
    List<MessageEntity> findLastNMessages(@Param("row_limit") Integer rowLimit);

    void deleteBySavedAtBefore(LocalDateTime since);
}
