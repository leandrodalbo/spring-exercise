package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.model.Address;
import com.exercise.TruProxyAPI.model.Company;
import com.exercise.TruProxyAPI.model.Officer;
import com.exercise.TruProxyAPI.proxyDto.TruProxyAddressDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyItemDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerItemDto;
import com.exercise.TruProxyAPI.repository.AddressRepository;
import com.exercise.TruProxyAPI.repository.CompanyRepository;
import com.exercise.TruProxyAPI.repository.OfficerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    CompanyRepository companyRepository;

    @Mock
    OfficerRepository officerRepository;

    @Mock
    TruProxyCallService truProxyCallService;

    @InjectMocks
    SearchService service;


    @Test
    void shouldSearchWithLocalQueries() {

        given(companyRepository.findByCompanyNumber(anyString())).willReturn(Optional.of(new Company(1L, 0, "abc123", "any", "any", "any", "any")));
        given(officerRepository.findByCompanyId(any())).willReturn(List.of(new Officer(1L, 0, "any", "any", "any", 1L)));
        given(addressRepository.findByCompanyId(any())).willReturn(Optional.of(new Address(1L, 0, "ANY", "any", "any", "any", "any", 1L, 1L)));
        given(addressRepository.findByOfficerId(any())).willReturn(Optional.of(new Address(1L, 0, "ANY", "any", "any", "any", "any", 1L, 1L)));


        var result = service.localSearch("abc2543");

        assertThat(result).isNotNull();
        verify(companyRepository, times(1)).findByCompanyNumber(anyString());
        verify(officerRepository, times(1)).findByCompanyId(any());
        verify(addressRepository, times(1)).findByOfficerId(any());
        verify(addressRepository, times(1)).findByCompanyId(any());
    }

    @Test
    void shouldSearchWithExternally() {

        given(truProxyCallService.findCompanies(anyString())).willReturn(List.of(new TruProxyCompanyItemDto("any", "any", "any", "any", "any", new TruProxyAddressDto("ANY", "ANY", "ANY", "ANY", "ANY"))));
        given(truProxyCallService.findOfficers(anyString())).willReturn(List.of(new TruProxyOfficerItemDto("any", "any", "any", null, new TruProxyAddressDto("ANY", "ANY", "ANY", "ANY", "ANY"))));

        var result = service.apiSearch("abc2543", false);

        assertThat(result).isNotNull();
        verify(truProxyCallService, times(1)).findOfficers(anyString());
        verify(truProxyCallService, times(1)).findCompanies(any());

    }
}
