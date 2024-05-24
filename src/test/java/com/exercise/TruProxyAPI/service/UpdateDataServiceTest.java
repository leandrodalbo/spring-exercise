package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.dto.ProxySearchResultItem;
import com.exercise.TruProxyAPI.model.Address;
import com.exercise.TruProxyAPI.model.Company;
import com.exercise.TruProxyAPI.model.Officer;
import com.exercise.TruProxyAPI.proxyDto.TruProxyAddressDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateDataServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    CompanyRepository companyRepository;

    @Mock
    OfficerRepository officerRepository;

    @InjectMocks
    UpdateDataService updateDataService;


    @Test
    void shouldSaveAnewCompany() {

        given(companyRepository.findByCompanyNumber(anyString())).willReturn(Optional.empty());
        given(companyRepository.save(any())).willReturn(new Company(1L, 0, "abc123", "any", "any", "any", "any"));
        given(officerRepository.save(any())).willReturn(new Officer(1L, 0, "any", "any", "any", 1L));
        given(addressRepository.save(any())).willReturn(new Address(1L, 0, "ANY", "any", "any", "any", "any", 1L, 1L));

        TruProxyAddressDto addressDto = new TruProxyAddressDto("ANY", "ANY", "ANY", "ANY", "ANY");
        List<TruProxyOfficerItemDto> officerItemDtos = List.of(new TruProxyOfficerItemDto("any", "any", "any", null, addressDto));
        ProxySearchResultItem searchResultItem = new ProxySearchResultItem("abc2432", "any", "any", "any", "any", addressDto, officerItemDtos);

        updateDataService.updateCompaniesAndOfficers(List.of(searchResultItem));

        verify(companyRepository, times(1)).findByCompanyNumber(anyString());
        verify(companyRepository, times(1)).save(any());
        verify(officerRepository, times(1)).save(any());
        verify(addressRepository, times(2)).save(any());
    }
}
