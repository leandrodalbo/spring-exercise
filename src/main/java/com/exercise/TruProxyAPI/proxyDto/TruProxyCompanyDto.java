package com.exercise.TruProxyAPI.proxyDto;

import java.util.List;

public record TruProxyCompanyDto(

        Integer total_results,
        List<TruProxyCompanyItemDto> items

) {
}
