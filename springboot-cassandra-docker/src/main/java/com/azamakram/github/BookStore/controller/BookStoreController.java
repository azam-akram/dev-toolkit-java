package com.azamakram.github.BookStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azamakram.github.BookStore.model.input.BookStoreInput;
import com.azamakram.github.BookStore.service.BookStoreService;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/books")
public class BookStoreController {

    private static Logger logger = LoggerFactory.getLogger(BookStoreController.class);
 
    @Autowired
    BookStoreService bookStoreService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBooks() {
        logger.info("Reading all books");
        return new ResponseEntity<>(bookStoreService.getAllBookStores(), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookById(@PathVariable(value = "uuid") final UUID uuid) {
        logger.info("Reading book information by Id");
        return new ResponseEntity<>(bookStoreService.getBookStoreById(uuid), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveBook(@Valid @RequestBody final BookStoreInput input) {
        logger.info("Saving book");
        return new ResponseEntity<>(bookStoreService.saveBookStore(input), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBook(@Valid @RequestBody final BookStoreInput input) {
        logger.info("Update book");
        return new ResponseEntity<>(bookStoreService.updateBookStore(input), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "uuid") final UUID uuid) {
        logger.info("Update book");
        bookStoreService.deleteBookStore(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}