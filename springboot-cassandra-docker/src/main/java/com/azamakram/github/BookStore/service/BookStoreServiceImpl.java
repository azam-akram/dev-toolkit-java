package com.azamakram.github.BookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azamakram.github.BookStore.model.entity.BookStore;
import com.azamakram.github.BookStore.model.exception.BookNotFoundException;
import com.azamakram.github.BookStore.model.input.BookStoreInput;
import com.azamakram.github.BookStore.model.output.BookStoreOutput;
import com.azamakram.github.BookStore.repository.BookStoreRepository;

import java.util.List;
import java.util.UUID;

@Component
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    public BookStoreServiceImpl(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public List<BookStoreOutput> getAllBookStores() {
        return ModelConverterUtil.convertEntityListToOutputList(bookStoreRepository.findAll());
    }

    @Override
    public BookStoreOutput getBookStoreById(UUID id) {
        return bookStoreRepository.findByUuid(id)
            .map(entity -> ModelConverterUtil.convertEntityToOutput(entity))
            .orElseThrow( ()-> new BookNotFoundException(String.format("Book not found for id: %s", id)));
    }

    @Override
    public BookStoreOutput saveBookStore(BookStoreInput input) {
        BookStore entity = ModelConverterUtil.convertInputToEntity(input);
        BookStore saved = bookStoreRepository.save(entity);
        if(saved == null) {
            throw new NullPointerException();
        }
        return ModelConverterUtil.convertEntityToOutput(saved);
    }

    @Override
    public BookStoreOutput updateBookStore(BookStoreInput input) {
        return ModelConverterUtil.convertEntityToOutput(
                bookStoreRepository.save(
                        ModelConverterUtil.convertInputToEntity(input)));
    }

    @Override
    public void deleteBookStore(UUID uuid) {
        bookStoreRepository.deleteById(uuid);
    }

}