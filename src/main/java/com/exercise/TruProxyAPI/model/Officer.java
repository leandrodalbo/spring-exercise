package com.exercise.TruProxyAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Officer(

        @Id
        Long officer_id,
        @Version
        Integer version,
        String name,
        String officer_role,
        String appointed_on,

        Long company_id
) {
}
