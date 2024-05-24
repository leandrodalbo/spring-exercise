package com.exercise.TruProxyAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Address(

        @Id
        Long address_id,

        @Version
        Integer version,

        String locality,
        String postal_code,
        String premises,
        String address_line_1,
        String country,

        Long company_id,
        Long officer_id
) {

}
