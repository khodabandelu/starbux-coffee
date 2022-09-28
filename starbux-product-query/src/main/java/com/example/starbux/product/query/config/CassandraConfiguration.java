package com.example.starbux.product.query.config;//package com.khodabandelu.starbux.product.config;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.cql.generator.CreateKeyspaceCqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

@Configuration
class CassandraConfiguration {
    @Bean
    CqlSession cqlSession(CqlSessionBuilder cqlSessionBuilder, CassandraProperties properties) {
        String keyspaceName = properties.getKeyspaceName();
        try (CqlSession session = cqlSessionBuilder.withKeyspace((CqlIdentifier) null).build()) {
            session.execute(CreateKeyspaceCqlGenerator.toCql(
                    CreateKeyspaceSpecification.createKeyspace(keyspaceName).ifNotExists()));
        }
        return cqlSessionBuilder.withKeyspace(keyspaceName).build();
    }

}