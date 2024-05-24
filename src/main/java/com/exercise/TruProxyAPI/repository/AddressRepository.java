package com.exercise.TruProxyAPI.repository;


import com.exercise.TruProxyAPI.model.Address;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    @Query("SELECT * FROM Address a WHERE a.company_id = :company_id")
    Optional<Address> findByCompanyId(Long company_id);


    @Query("SELECT * FROM Address a WHERE a.officer_id = :officer_id")
    Optional<Address> findByOfficerId(Long officer_id);

}