package com.exercise.TruProxyAPI.dto;

import com.exercise.TruProxyAPI.proxyDto.TruProxyAddressDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerItemDto;

import java.util.List;

public record ProxySearchResultItem(String company_number,
                                    String company_type,
                                    String title,
                                    String company_status,
                                    String date_of_creation,

                                    TruProxyAddressDto address,
                                    List<TruProxyOfficerItemDto> officers
) {

}
