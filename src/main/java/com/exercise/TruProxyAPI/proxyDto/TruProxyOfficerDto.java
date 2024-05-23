package com.exercise.TruProxyAPI.proxyDto;

import java.util.List;

public record TruProxyOfficerDto(

        Integer items_per_page,
        List<TruProxyOfficerItemDto> items

) {
}
