package com.azamakram.github.springbootjpadocker.service;

import com.azamakram.github.springbootjpadocker.model.entity.MessageEntity;
import com.azamakram.github.springbootjpadocker.model.output.MessageOutput;

import java.util.List;
import java.util.stream.StreamSupport;

public class EntityToOutputConverter {

    private EntityToOutputConverter() {}

    public static List<MessageOutput> convertMessageEntitiesToOutput(Iterable<MessageEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(EntityToOutputConverter::convertMessageEntityToOutput)
                .toList();
    }

    public static MessageOutput convertMessageEntityToOutput(MessageEntity entity) {
        return new MessageOutput(entity.getMessageKey(), entity.getSender(), entity.getSavedAt());
    }
}
