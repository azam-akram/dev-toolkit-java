package com.azamakram.github.springbootjpadocker.service;

import org.springframework.stereotype.Component;
import com.azamakram.github.springbootjpadocker.repository.MessageRepository;
import com.azamakram.github.springbootjpadocker.model.entity.MessageEntity;
import com.azamakram.github.springbootjpadocker.model.exception.MessageNotFoundException;
import com.azamakram.github.springbootjpadocker.model.input.MessageInput;
import com.azamakram.github.springbootjpadocker.model.output.MessageOutput;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class MessageService {

    private static final long SECONDS_ALLOWED_TO_UPDATE = 10L;
    private static final long MINUTES_ALLOWED_TO_DELETE = 2L;

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageOutput saveMessage(MessageInput messageInput) {
        var entity = MessageEntity.builder()
                .messageKey(UUID.randomUUID().toString())
                .sender(messageInput.sender())
                .savedAt(LocalDateTime.now())
                .build();
        return EntityToOutputConverter.convertMessageEntityToOutput(messageRepository.save(entity));
    }

    public List<MessageOutput> getAllMessages() {
        return EntityToOutputConverter.convertMessageEntitiesToOutput(messageRepository.findAll());
    }

    public List<MessageOutput> getNNumberOfMessages(Integer count) {
        return EntityToOutputConverter.convertMessageEntitiesToOutput(messageRepository.findLastNMessages(count));
    }

    @Transactional
    public MessageOutput updateMessage(String messageKey, MessageInput messageInput) {
        LocalDateTime after = LocalDateTime.now().minusSeconds(SECONDS_ALLOWED_TO_UPDATE);
        return messageRepository.findByMessageKeyAndSavedAtAfter(messageKey, after)
                .map(entity -> {
                    entity.setSender(messageInput.sender());
                    return EntityToOutputConverter.convertMessageEntityToOutput(messageRepository.save(entity));
                })
                .orElseThrow(() ->
                        new MessageNotFoundException("Message not found for key %s.".formatted(messageKey)));
    }

    @Transactional
    public void deleteMessage() {
        LocalDateTime since = LocalDateTime.now().minusMinutes(MINUTES_ALLOWED_TO_DELETE);
        messageRepository.deleteBySavedAtBefore(since);
    }
}
