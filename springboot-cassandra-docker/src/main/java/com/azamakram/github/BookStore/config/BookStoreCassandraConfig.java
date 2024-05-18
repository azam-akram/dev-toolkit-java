package com.azamakram.github.BookStore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.azamakram.github.BookStore.repository"})
public class BookStoreCassandraConfig extends AbstractCassandraConfiguration {
    
    @Value("${spring.cassandra.username}")
    private String username;

    @Value("${spring.cassandra.password}")
    private String password;

    @Value("${spring.cassandra.port}")
    private int port;

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.cassandra.keyspace-name}")
    private String keySpace;

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.azamakram.github.BookStore.model.entity"};
    }


    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification
            .createKeyspace(getKeyspaceName())
            .ifNotExists()
            .with(KeyspaceOption.DURABLE_WRITES, true)
            .withSimpleReplication());
    }

    @Bean
    @Override
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean cassandraSession = super.cassandraSession();//super session should be called only once
        cassandraSession.setKeyspaceCreations(getKeyspaceCreations());
        cassandraSession.setContactPoints(contactPoints);
        cassandraSession.setPort(port);
        cassandraSession.setUsername(username);
        cassandraSession.setPassword(password);
        return cassandraSession;
    }

}
