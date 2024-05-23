package com.exercise.TruProxyAPI.proxyDto;

public record TruProxyAddressDto(
        String locality,
        String postal_code,
        String premises,
        String address_line_1,
        String country
) {
}
