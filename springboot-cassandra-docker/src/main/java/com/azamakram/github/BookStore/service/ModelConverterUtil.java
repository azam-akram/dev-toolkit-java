package com.azamakram.github.BookStore.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.azamakram.github.BookStore.model.entity.BookStore;
import com.azamakram.github.BookStore.model.input.BookStoreInput;
import com.azamakram.github.BookStore.model.output.BookStoreOutput;

public class ModelConverterUtil {

    public static List<BookStoreOutput> convertEntityListToOutputList(Iterable<BookStore> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map( entity -> convertEntityToOutput(entity))
                .collect(Collectors.toList());
    }

    public static BookStoreOutput convertEntityToOutput(BookStore entity) {
        return BookStoreOutput.builder()
                .uuid(entity.getUuid())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .build();
    }


    public static BookStore convertInputToEntity(BookStoreInput input) {
        return BookStore.builder()
                .uuid(input.getUuid())
                .title(input.getTitle())
                .writer(input.getWriter())
                .build();
    }
}
