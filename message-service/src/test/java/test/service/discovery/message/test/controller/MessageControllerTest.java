package test.service.discovery.message.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.service.discovery.message.controller.MessageController;
import test.service.discovery.message.model.entity.MessageEntity;
import test.service.discovery.message.model.input.MessageInput;
import test.service.discovery.message.model.output.MessageOutput;
import test.service.discovery.message.service.EntityToOutputConverter;
import test.service.discovery.message.service.MessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void getAllMessages_shouldGetAllMessages() throws Exception {

        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key").sender("a sender").savedAt(LocalDateTime.now()).build();

        List<MessageEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        List<MessageOutput> messageOutputList = EntityToOutputConverter.convertMessageEntitiesToOutput(entityList);

        given(messageService.getAllMessages()).willReturn(messageOutputList);

        this.mockMvc.perform(get("/message"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getNNumberOfMessages_shouldGetRequiredMessages() throws Exception {

        MessageEntity entity1 = MessageEntity.builder()
                .messageKey("message-key-1").sender("a sender").savedAt(LocalDateTime.now()).build();
        MessageEntity entity2 = MessageEntity.builder()
                .messageKey("message-key-2").sender("b sender").savedAt(LocalDateTime.now()).build();

        List<MessageEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);

        List<MessageOutput> messageOutputList = EntityToOutputConverter.convertMessageEntitiesToOutput(entityList);

        given(messageService.getNNumberOfMessages(2)).willReturn(messageOutputList);

        this.mockMvc.perform(get("/message/2"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postNewMessage_shouldSaveMessage() throws Exception {
        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key-1").sender("a sender").savedAt(LocalDateTime.now()).build();

        MessageOutput output = EntityToOutputConverter.convertMessageEntityToOutput(entity);
        String input = "{\"sender\":\"new sender\"}";
        given(messageService.saveMessage(any(MessageInput.class))).willReturn(output);

        this.mockMvc.perform(post("/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(input))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/message/" + output.getMessageKey()));
        verify(this.messageService, times(1)).saveMessage(any(MessageInput.class));
    }

    @Test
    public void updateNewResourceExecution_shouldSaveExecution() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.toString();
        String outputStr = "{\"messageKey\":\"message-key-1\",\"sender\":\"changed sender\",\"savedAt\":\"" + nowStr + "\"}";

        MessageEntity entity = MessageEntity.builder()
                .messageKey("message-key-1").sender("changed sender").savedAt(now).build();
        MessageOutput output = EntityToOutputConverter.convertMessageEntityToOutput(entity);
        given(messageService.updateMessage(any(String.class), any(MessageInput.class))).willReturn(output);

        String input = "{\"sender\":\"changed sender\"}";

        this.mockMvc.perform(put("/message/message-key-1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(input))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(outputStr));
        verify(this.messageService, times(1)).updateMessage(any(String.class), any(MessageInput.class));
    }

}
