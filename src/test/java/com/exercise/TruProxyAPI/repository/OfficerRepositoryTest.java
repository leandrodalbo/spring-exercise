package com.exercise.TruProxyAPI.repository;

import com.exercise.TruProxyAPI.model.Company;
import com.exercise.TruProxyAPI.model.Officer;
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
public class OfficerRepositoryTest {

    @ServiceConnection
    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15-alpine").withReuse(false);
    @Autowired
    OfficerRepository officerRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    JdbcAggregateTemplate template;

    @Test
    void shouldFindOfficersByCompanyId() {
        template.insert(new Company(1L, 0, "abc324", "big_one", "unknown", "active", "20/12/2019"));
        template.insert(new Officer(null, 0, "ANY", "unknown", "unknown", 1L));

        var officers = officerRepository.findByCompanyId(1L);

        assertThat(officers.size()).isEqualTo(1);

    }

}
