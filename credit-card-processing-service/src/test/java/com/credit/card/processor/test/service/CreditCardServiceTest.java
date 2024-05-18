package com.credit.card.processor.test.service;

import com.credit.card.processor.test.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.credit.card.processor.repository.CreditCardRepository;
import com.credit.card.processor.CreditCardApplication;
import com.credit.card.processor.model.entity.CreditCardEntity;
import com.credit.card.processor.model.input.CreditCardInput;
import com.credit.card.processor.model.output.CreditCardOutput;
import com.credit.card.processor.service.EntityToOutputConverter;
import com.credit.card.processor.service.CreditCardService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(classes = { CreditCardApplication.class })
@RunWith(SpringRunner.class)
public class CreditCardServiceTest {

    private CreditCardService creditCardService;

    @Mock
    private CreditCardRepository creditCardRepositoryMock;

    @Before
    public void setup() {
        this.creditCardService = new CreditCardService(creditCardRepositoryMock);
    }

    @Test
    public void saveNewCards_shouldReturnSavedCards() {
        CreditCardEntity entity = TestUtil.createTestEntity();
        CreditCardOutput expectedCard = EntityToOutputConverter.convertCreditCardsEntityToOutput(entity);
        when(creditCardRepositoryMock.save(any(CreditCardEntity.class))).thenReturn(entity);
        CreditCardInput input = TestUtil.createTestInput();

        CreditCardOutput output = creditCardService.saveNewCreditCard(input);
        assertThat(expectedCard.getCardNumber()).isEqualTo(output.getCardNumber());
    }

    @Test
    public void fetchAllCreditCards_shouldReturnAllRecords() {
        CreditCardEntity entity = TestUtil.createTestEntity();

        List<CreditCardEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        List<CreditCardOutput> expectedList = EntityToOutputConverter.convertCreditCardsEntitiesToOutput(entityList);

        when(creditCardRepositoryMock.findAll()).thenReturn(entityList);
        List<CreditCardOutput> returnedList = creditCardService.getAllCardRecords();
        assertThat(expectedList.size()).isEqualTo(returnedList.size());
        assertThat(returnedList.get(0).getName()).isEqualTo("Customer");
    }

}