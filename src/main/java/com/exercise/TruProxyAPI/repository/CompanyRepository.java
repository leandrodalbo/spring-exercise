package com.exercise.TruProxyAPI.repository;


import com.exercise.TruProxyAPI.model.Company;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    @Query("SELECT * FROM Company c WHERE c.company_number = :company_number")
    Optional<Company> findByCompanyNumber(String company_number);

}