package com.credit.card.processor.test.integrationtest;

import com.credit.card.processor.test.TestUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import com.credit.card.processor.repository.CreditCardRepository;
import com.credit.card.processor.model.entity.CreditCardEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CreditCardServiceIntegrationTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    @Sql(scripts = "classpath:db/data.sql")
    public void fetchAllCardRecords() {
        assertThat(Lists.newArrayList(creditCardRepository.findAll()).size()).isEqualTo(5);
        List<CreditCardEntity> entities = Lists.newArrayList(creditCardRepository.findAll());
        assertThat(entities.size()).isEqualTo(5);
        assertThat(entities.get(0).getId()).isEqualTo(1);
        assertThat(entities.get(1).getId()).isEqualTo(2);
        assertThat(entities.get(2).getId()).isEqualTo(3);
        assertThat(entities.get(3).getId()).isEqualTo(4);
        assertThat(entities.get(4).getId()).isEqualTo(5);
    }

    @Test
    public void saveAndFetchRecord() {
        CreditCardEntity entity = TestUtil.createTestEntity();
        CreditCardEntity saved = creditCardRepository.save(entity);
        assertThat(saved.getCardNumber().equals("1111 2222 3333 4444"));
    }
}