package test.service.discovery.message.test.integrationtest;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import test.service.discovery.message.repository.MessageRepository;
import test.service.discovery.message.model.entity.MessageEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessageServiceIntegrationTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    @Sql(scripts = "classpath:db/data.sql")
    public void fetchAllMessageRecords() {
        assertThat(Lists.newArrayList(messageRepository.findAll()).size()).isEqualTo(5);
        List<MessageEntity> entities = Lists.newArrayList(messageRepository.findAll());
        assertThat(entities.size()).isEqualTo(5);
        assertThat(entities.get(0).getId()).isEqualTo(1);
        assertThat(entities.get(1).getId()).isEqualTo(2);
        assertThat(entities.get(2).getId()).isEqualTo(3);
        assertThat(entities.get(3).getId()).isEqualTo(4);
        assertThat(entities.get(4).getId()).isEqualTo(5);
    }

    @Test
    @Sql(scripts = "classpath:db/data.sql")
    public void fetchLast2Records() {
        assertThat(Lists.newArrayList(messageRepository.findTop2ByOrderByIdDesc()).size()).isEqualTo(2);
        List<MessageEntity> entities = Lists.newArrayList(messageRepository.findTop2ByOrderByIdDesc());
        assertThat(entities.size()).isEqualTo(2);
    }

    @Test
    @Sql(scripts = "classpath:db/data.sql")
    public void fetchLastNRecords() {
        assertThat(Lists.newArrayList(messageRepository.findLastNEmployees(2)).size()).isEqualTo(2);
        List<MessageEntity> entities = Lists.newArrayList(messageRepository.findLastNEmployees(2));
        assertThat(entities.size()).isEqualTo(2);
    }

    @Test
    public void saveAndFetchRecord() {
        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key").sender("a sender").savedAt(LocalDateTime.now()).build();

        MessageEntity saved = messageRepository.save(entity);
        assertThat(saved.getMessageKey().equals("message-key"));
    }

    @Test
    public void updateAndFetchRecord() {
        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key").sender("a sender").savedAt(LocalDateTime.now()).build();
        messageRepository.save(entity);

        LocalDateTime after = LocalDateTime.now().minusSeconds(1);

        Optional<MessageEntity> entityOptional =
            messageRepository.findByMessageKeyAndSavedAtAfter("message-key", after);

        MessageEntity savedEntity = entityOptional.get();
        savedEntity.setSender("updated sender");
        MessageEntity updated = messageRepository.save(savedEntity);
        assertThat(updated.getMessageKey().equals("updated sender"));
    }

    @Test
    public void deleteMessageWaitFor2Seconds_ReturnedMessageListShouldNotIncludeDeletedMessage()
            throws InterruptedException {

        MessageEntity entity1 = MessageEntity.builder()
                .messageKey("message-key 1").sender("a sender").savedAt(LocalDateTime.now()).build();
        messageRepository.save(entity1);

        Thread.sleep(2000);

        MessageEntity entity2 = MessageEntity.builder()
                .messageKey("message-key 2").sender("b sender").savedAt(LocalDateTime.now()).build();
        messageRepository.save(entity2);

        assertThat(Lists.newArrayList(messageRepository.findAll()).size()).isEqualTo(2);

        LocalDateTime since = LocalDateTime.now().minusSeconds(1);
        messageRepository.deleteBySavedAtBefore(since);

        assertThat(Lists.newArrayList(messageRepository.findAll()).size()).isEqualTo(1);
        List<MessageEntity> all = messageRepository.findAll();
        assertThat(all.stream().anyMatch(e -> e.getMessageKey().equals("message-key 1"))).isFalse();
    }
}