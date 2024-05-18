package com.credit.card.processor.test;

import com.credit.card.processor.model.entity.CreditCardEntity;
import com.credit.card.processor.model.input.CreditCardInput;

public class TestUtil {

    public static CreditCardEntity createTestEntity() {
        return CreditCardEntity.builder()
                .name("Customer")
                .cardNumber("1111 2222 3333 4444")
                .accountLimit(0)
                .build();
    }

    public static CreditCardInput createTestInput() {
        return CreditCardInput.builder()
                .name("Customer")
                .cardNumber("1111 2222 3333 4444")
                .accountLimit(0)
                .build();
    }
}
