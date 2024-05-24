package com.exercise.TruProxyAPI.repository;

import com.exercise.TruProxyAPI.model.Officer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficerRepository extends CrudRepository<Officer, Long> {

    @Query("SELECT * FROM Officer o WHERE o.company_id = :company_id")
    List<Officer> findByCompanyId(Long company_id);
}