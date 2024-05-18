package test.service.discovery.message.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import test.service.discovery.message.repository.MessageRepository;
import test.service.discovery.message.service.ServiceDiscoveryHelper;
import test.service.discovery.message.MessageApplication;
import test.service.discovery.message.model.entity.MessageEntity;
import test.service.discovery.message.model.exception.MessageNotFoundException;
import test.service.discovery.message.model.input.MessageInput;
import test.service.discovery.message.model.output.MessageOutput;
import test.service.discovery.message.service.EntityToOutputConverter;
import test.service.discovery.message.service.MessageService;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(classes = { MessageApplication.class })
@RunWith(SpringRunner.class)
public class MessageServiceTest {

    private MessageService messageService;

    @Mock
    private MessageRepository messageRepositoryMock;

    @Mock
    private ServiceDiscoveryHelper serviceDiscoveryMock;

    @Before
    public void setup() {
        this.messageService = new MessageService(messageRepositoryMock, serviceDiscoveryMock);
    }

    @Test
    public void getAllMessages_shouldReturnAllMessages() {
        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key").sender("a sender").savedAt(LocalDateTime.now()).build();
        List<MessageEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        List<MessageOutput> expectedList = EntityToOutputConverter.convertMessageEntitiesToOutput(entityList);

        when(messageRepositoryMock.findAll()).thenReturn(entityList);
        List<MessageOutput> returnedList = messageService.getAllMessages();
        assertThat(expectedList.size()).isEqualTo(returnedList.size());
        assertThat(returnedList.get(0).getMessageKey()).isEqualTo("message-key");
    }

    @Test
    public void saveAllMessages_shouldReturnSavedMessage() throws ServiceUnavailableException {
        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key").sender("a sender").savedAt(LocalDateTime.now()).build();
        MessageOutput expectedMessage = EntityToOutputConverter.convertMessageEntityToOutput(entity);

        MessageInput input = MessageInput.builder().sender("a sender").build();

        when(messageRepositoryMock.save(any(MessageEntity.class))).thenReturn(entity);
        when(serviceDiscoveryMock.getUuidFromUuidGeneratorService()).thenReturn("message-key");

        MessageOutput output = messageService.saveMessage(input);
        assertThat(expectedMessage.getMessageKey()).isEqualTo(output.getMessageKey());
    }

    @Test(expected = MessageNotFoundException.class)
    public void updateMessageWithIncorrectKey_shouldReturnMessageNotFoundException() {
        MessageInput input = MessageInput.builder().sender("a sender").build();
        messageService.updateMessage("Non existing key",input);
    }

    @Test
    public void updateMessage_shouldReturnUpdatedMessage() {
        MessageInput input = MessageInput.builder().sender("a sender").build();

        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key").sender("a sender").savedAt(LocalDateTime.now()).build();
        MessageOutput expectedMessage = EntityToOutputConverter.convertMessageEntityToOutput(entity);

        Optional<MessageEntity> entityOptional = Optional.of(entity);

        when(messageRepositoryMock.findByMessageKeyAndSavedAtAfter(any(String.class), any(LocalDateTime.class)))
                .thenReturn(entityOptional);
        when(messageRepositoryMock.save(any(MessageEntity.class))).thenReturn(entity);

        MessageOutput output = messageService.updateMessage("message-key",input);
        assertThat(expectedMessage.getMessageKey()).isEqualTo(output.getMessageKey());
    }
}
