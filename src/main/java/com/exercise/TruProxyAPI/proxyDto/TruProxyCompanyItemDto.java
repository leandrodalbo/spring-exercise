package com.exercise.TruProxyAPI.proxyDto;

public record TruProxyCompanyItemDto(
        String company_number,
        String company_type,
        String title,
        String company_status,
        String date_of_creation,

        TruProxyAddressDto address
) {
}
