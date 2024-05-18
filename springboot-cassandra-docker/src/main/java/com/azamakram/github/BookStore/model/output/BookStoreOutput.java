package com.azamakram.github.BookStore.model.output;

import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookStoreOutput {

    private UUID uuid;

    private String title;

    private String writer;
}