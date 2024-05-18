package com.credit.card.processor.test.controller;

import com.credit.card.processor.test.TestUtil;
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
import com.credit.card.processor.controller.CreditCardController;
import com.credit.card.processor.model.entity.CreditCardEntity;
import com.credit.card.processor.model.input.CreditCardInput;
import com.credit.card.processor.model.output.CreditCardOutput;
import com.credit.card.processor.service.EntityToOutputConverter;
import com.credit.card.processor.service.CreditCardService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardService creditCardService;

    @Test
    public void getAllCards_shouldGetAllCard() throws Exception {

        CreditCardEntity entity = TestUtil.createTestEntity();

        List<CreditCardEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        List<CreditCardOutput> creditCardOutputList = EntityToOutputConverter.convertCreditCardsEntitiesToOutput(entityList);

        given(creditCardService.getAllCardRecords()).willReturn(creditCardOutputList);

        this.mockMvc.perform(get("/card"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createNewCreditCard_shouldSaveCardInformation() throws Exception {
        CreditCardEntity entity = TestUtil.createTestEntity();
        CreditCardOutput output = EntityToOutputConverter.convertCreditCardsEntityToOutput(entity);

        String input = "{\"name\":\"new name\",\"cardNumber\":\"1111 2222 3333 4444\",\"accountLimit\":\"100000\"}";
        given(creditCardService.saveNewCreditCard(any(CreditCardInput.class))).willReturn(output);

        this.mockMvc.perform(post("/card")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(input))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/card/" + output.getName()));
        verify(this.creditCardService, times(1)).saveNewCreditCard(any(CreditCardInput.class));
    }

}
