package com.credit.card.processor.model.output;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardOutput {
    @NotBlank
    private String name;

    @NotBlank
    private String cardNumber;

    @NotNull
    private Integer balance;

    @NotNull
    private Integer limit;
}