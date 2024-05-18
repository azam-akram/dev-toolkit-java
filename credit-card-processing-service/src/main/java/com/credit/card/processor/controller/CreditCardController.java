package com.credit.card.processor.controller;

import com.credit.card.processor.model.input.CreditCardInput;
import com.credit.card.processor.model.output.CreditCardOutput;
import com.credit.card.processor.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/card")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAllCreditCards() {
        log.trace("Getting all the credit card information");
        List<CreditCardOutput> creditCardOutputList = creditCardService.getAllCardRecords();
        return new ResponseEntity<>(creditCardOutputList, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createNewCreditCard(@RequestBody final CreditCardInput creditCardInput) {
        log.trace("Saving new credit card");
        CreditCardOutput output = creditCardService.saveNewCreditCard(creditCardInput);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(output.getName())
                .toUri();
        return ResponseEntity.created(location).body(output);
    }

}

