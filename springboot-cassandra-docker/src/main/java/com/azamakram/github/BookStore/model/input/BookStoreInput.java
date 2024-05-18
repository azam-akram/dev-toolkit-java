package com.azamakram.github.BookStore.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookStoreInput {

    private UUID uuid;

    private String title;

    private String writer;
}