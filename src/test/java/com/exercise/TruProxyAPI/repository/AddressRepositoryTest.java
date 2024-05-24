package com.exercise.TruProxyAPI.repository;

import com.exercise.TruProxyAPI.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {

    @ServiceConnection
    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15-alpine").withReuse(false);
    @Autowired
    AddressRepository repository;
    @Autowired
    JdbcAggregateTemplate template;

    @Test
    void shouldFindAddressByCompanyId() {

        template.insert(new Address(1L, 0, "ANY", "unknown", "unknown", "unknown", "unknown", 1L, null));

        var address = repository.findByCompanyId(1L);

        assertThat(address.isEmpty()).isFalse();

    }

    @Test
    void shouldFindAddressByOfficerId() {

        template.insert(new Address(1L, 0, "ANY", "unknown", "unknown", "unknown", "unknown", null, 1L));

        var address = repository.findByOfficerId(1L);

        assertThat(address.isEmpty()).isFalse();

    }
}
