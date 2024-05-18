package com.demo.kafka.boot.spring.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @NotNull
    private String uuid;

    @NotBlank
    private String from;

    @NotBlank
    private String to;
}
