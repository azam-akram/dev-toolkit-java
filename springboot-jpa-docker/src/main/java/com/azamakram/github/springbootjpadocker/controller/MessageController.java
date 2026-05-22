package com.azamakram.github.springbootjpadocker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azamakram.github.springbootjpadocker.model.input.MessageInput;
import com.azamakram.github.springbootjpadocker.model.output.MessageOutput;
import com.azamakram.github.springbootjpadocker.service.MessageService;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageOutput>> getAllMessages() {
        log.trace("Getting all messages");
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping(path = "/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageOutput>> getNumberOfLastSavedMessages(@PathVariable Integer count) {
        log.trace("Getting {} last saved message records", count);
        return ResponseEntity.ok(messageService.getNNumberOfMessages(count));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageOutput> createNewMessage(@Valid @RequestBody MessageInput messageInput) {
        log.trace("Saving new message");
        MessageOutput output = messageService.saveMessage(messageInput);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{messageKey}").buildAndExpand(output.messageKey()).toUri();
        return ResponseEntity.created(location).body(output);
    }

    @PutMapping(path = "/{messageKey}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageOutput> updateMessage(@PathVariable String messageKey,
                                                       @Valid @RequestBody MessageInput messageInput) {
        log.trace("Updating message with key {}", messageKey);
        return ResponseEntity.ok(messageService.updateMessage(messageKey, messageInput));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMessage() {
        log.trace("Deleting old messages");
        messageService.deleteMessage();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
