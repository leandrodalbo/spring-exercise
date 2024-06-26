package com.exercise.TruProxyAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Company(
        @Id
        Long company_id,
        @Version
        Integer version,
        String company_number,
        String company_type,
        String title,
        String company_status,
        String date_of_creation
) {
}
