package com.azamakram.github.BookStore.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.azamakram.github.BookStore.model.entity.BookStore;

import java.util.Optional;
import java.util.UUID;

public interface BookStoreRepository extends CassandraRepository<BookStore, UUID> {
    Optional<BookStore> findByUuid(UUID uuid);
}
