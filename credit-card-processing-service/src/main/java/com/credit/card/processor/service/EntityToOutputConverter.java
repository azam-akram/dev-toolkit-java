package com.credit.card.processor.service;

import com.credit.card.processor.model.entity.CreditCardEntity;
import com.credit.card.processor.model.output.CreditCardOutput;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EntityToOutputConverter {

    private EntityToOutputConverter() {
        super();
    }

    public static List<CreditCardOutput> convertCreditCardsEntitiesToOutput(
            Iterable<CreditCardEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(c -> convertCreditCardsEntityToOutput(c))
                .collect(Collectors.toList());
    }

    public static CreditCardOutput convertCreditCardsEntityToOutput(CreditCardEntity entity) {
        return CreditCardOutput.builder()
            .name(entity.getName())
            .cardNumber(entity.getCardNumber())
            .balance(entity.getBalance())
            .limit(entity.getAccountLimit())
            .build();
    }
}