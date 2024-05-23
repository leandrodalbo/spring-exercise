package com.exercise.TruProxyAPI.model;

public record Company(
        String company_number,
        String company_type,
        String title,
        String company_status,
        String date_of_creation
) {
}
