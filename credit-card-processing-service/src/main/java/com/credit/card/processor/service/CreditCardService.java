package com.credit.card.processor.service;

import com.credit.card.processor.model.entity.CreditCardEntity;
import com.credit.card.processor.model.exception.CreditCardAlreadyExistsException;
import com.credit.card.processor.model.input.CreditCardInput;
import com.credit.card.processor.model.output.CreditCardOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.credit.card.processor.repository.CreditCardRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository repository) {
        this.creditCardRepository = repository;
    }

    @Transactional
    public CreditCardOutput saveNewCreditCard(final CreditCardInput creditCardInput) {
        CreditCardEntity entity = CreditCardEntity.builder()
                .name(creditCardInput.getName())
                .cardNumber(creditCardInput.getCardNumber())
                .balance(0)
                .accountLimit(creditCardInput.getAccountLimit())
                .build();

        CreditCardEntity entityToSave = creditCardRepository.save(entity);
        Optional.of(creditCardRepository.save(entity))
            .orElseThrow( () ->
                new CreditCardAlreadyExistsException(String.format("Credit card with this number already exists %s.",
                    creditCardInput.getCardNumber())));

        return EntityToOutputConverter.convertCreditCardsEntityToOutput(entityToSave);
    }

    public List<CreditCardOutput> getAllCardRecords() {
        List<CreditCardEntity> entities = creditCardRepository.findAll();
        if(entities.size() == 0) {
            return Collections.emptyList();
        }
        return EntityToOutputConverter.convertCreditCardsEntitiesToOutput(entities);
    }
}
