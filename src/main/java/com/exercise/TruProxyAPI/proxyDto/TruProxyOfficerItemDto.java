package com.exercise.TruProxyAPI.proxyDto;

public record TruProxyOfficerItemDto(
        String name,
        String officer_role,
        String appointed_on,

        String resigned_on,
        TruProxyAddressDto address
) {
}
