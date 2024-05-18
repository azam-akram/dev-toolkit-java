package com.azamakram.github.BookStore.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("bookstore")
public class BookStore {

    @PrimaryKeyColumn(name = "uuid", type = PrimaryKeyType.PARTITIONED)
    private UUID uuid;

    @Column(value = "title")
    private String title;

    @Column(value = "writer")
    private String writer;
}