package test.service.discovery.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.service.discovery.message.repository.MessageRepository;
import test.service.discovery.message.model.entity.MessageEntity;
import test.service.discovery.message.model.exception.MessageNotFoundException;
import test.service.discovery.message.model.input.MessageInput;
import test.service.discovery.message.model.output.MessageOutput;

import javax.naming.ServiceUnavailableException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MessageService {

    private Long TIME_TO_UPDATE = 10L;

    private Long TIME_TO_DELETE = 2L;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ServiceDiscoveryHelper serviceDiscovery;

    public MessageService(MessageRepository repository, ServiceDiscoveryHelper serviceDiscovery) {
        this.messageRepository = repository;
        this.serviceDiscovery = serviceDiscovery;
    }

    @Transactional
    public MessageOutput saveMessage(final MessageInput messageInput) throws ServiceUnavailableException {

        String messageKey = serviceDiscovery.getUuidFromUuidGeneratorService();

        MessageEntity entity = MessageEntity.builder()
                .messageKey(messageKey).sender(messageInput.getSender()).savedAt(LocalDateTime.now()).build();

        return EntityToOutputConverter.convertMessageEntityToOutput(messageRepository.save(entity));
    }

    public List<MessageOutput> getAllMessages() {
        return EntityToOutputConverter.convertMessageEntitiesToOutput(messageRepository.findAll());
    }

    public List<MessageOutput> getNNumberOfMessages(Integer count) {
        return EntityToOutputConverter.convertMessageEntitiesToOutput(messageRepository.findLastNEmployees(count));
    }

    @Transactional
    public MessageOutput updateMessage(String messageKey, MessageInput messageInput) {
        LocalDateTime after = LocalDateTime.now().minusSeconds(TIME_TO_UPDATE);

        return messageRepository.findByMessageKeyAndSavedAtAfter(messageKey, after)
            .map(entity -> {
                entity.setSender(messageInput.getSender());
                MessageEntity savedEntity = messageRepository.save(entity);
                if(savedEntity == null) {
                    return null;
                }
                return EntityToOutputConverter.convertMessageEntityToOutput(savedEntity);
            }).orElseThrow( () ->
                    new MessageNotFoundException(String.format("Message not found for key %s.", messageKey)));
    }

    @Transactional
    public void deleteMessage() {
        LocalDateTime since = LocalDateTime.now().minusMinutes(TIME_TO_DELETE);
        messageRepository.deleteBySavedAtBefore(since);
    }

}
