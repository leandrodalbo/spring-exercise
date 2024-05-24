package com.exercise.TruProxyAPI.repository;

import com.exercise.TruProxyAPI.model.Company;
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
public class CompanyRepositoryTest {

    @ServiceConnection
    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15-alpine").withReuse(false);
    @Autowired
    CompanyRepository repository;
    @Autowired
    JdbcAggregateTemplate template;

    @Test
    void shouldFindCompanyByNumber() {

        template.insert(new Company(null, 0, "abc324", "big_one", "unknown", "active", "20/12/2019"));

        var company = repository.findByCompanyNumber("abc324");

        assertThat(company.isEmpty()).isFalse();

    }
}
