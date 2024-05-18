package com.azamakram.github.BookStore.service;

import java.util.List;
import java.util.UUID;

import com.azamakram.github.BookStore.model.input.BookStoreInput;
import com.azamakram.github.BookStore.model.output.BookStoreOutput;

public interface BookStoreService {
    List<BookStoreOutput> getAllBookStores();

    BookStoreOutput getBookStoreById(UUID id);

    BookStoreOutput saveBookStore(BookStoreInput input);

    BookStoreOutput updateBookStore(BookStoreInput input);

    void deleteBookStore(UUID id);
}
