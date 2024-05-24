package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.dto.ProxySearchResult;
import com.exercise.TruProxyAPI.dto.ProxySearchResultItem;
import com.exercise.TruProxyAPI.model.Address;
import com.exercise.TruProxyAPI.model.Company;
import com.exercise.TruProxyAPI.model.Officer;
import com.exercise.TruProxyAPI.proxyDto.TruProxyAddressDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyItemDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerItemDto;
import com.exercise.TruProxyAPI.repository.AddressRepository;
import com.exercise.TruProxyAPI.repository.CompanyRepository;
import com.exercise.TruProxyAPI.repository.OfficerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final CompanyRepository companyRepository;
    private final OfficerRepository officerRepository;
    private final AddressRepository addressRepository;

    private final TruProxyCallService truProxyCallService;

    public SearchService(CompanyRepository companyRepository, OfficerRepository officerRepository, AddressRepository addressRepository, TruProxyCallService truProxyCallService) {
        this.companyRepository = companyRepository;
        this.officerRepository = officerRepository;
        this.addressRepository = addressRepository;
        this.truProxyCallService = truProxyCallService;
    }

    public ProxySearchResult apiSearch(String input, Boolean active) {
        List<TruProxyCompanyItemDto> companies;

        if (active) {
            companies = truProxyCallService.findCompanies(input).stream().filter(companyItemDto -> "active".equals(companyItemDto.company_status())).toList();
        } else {
            companies = truProxyCallService.findCompanies(input);
        }

        return new ProxySearchResult(
                companies.size(),
                companies.stream().map(companyItemDto -> {

                    List<TruProxyOfficerItemDto> officers = truProxyCallService.findOfficers(companyItemDto.company_number());

                    return new ProxySearchResultItem(
                            companyItemDto.company_number(),
                            companyItemDto.company_type(),
                            companyItemDto.title(),
                            companyItemDto.company_status(),
                            companyItemDto.date_of_creation(),
                            companyItemDto.address(),
                            officers
                    );

                }).toList());


    }

    public ProxySearchResult localSearch(String companyNumber) {

        Optional<Company> company = companyRepository.findByCompanyNumber(companyNumber);

        if (company.isEmpty()) {
            return new ProxySearchResult(
                    0,
                    null
            );
        } else {
            Company c = company.get();
            Address companyAddress = addressRepository.findByCompanyId(c.company_id()).get();
            List<Officer> officers = officerRepository.findByCompanyId(c.company_id());

            return new ProxySearchResult(
                    1,
                    List.of(new ProxySearchResultItem(
                            c.company_number(),
                            c.company_type(),
                            c.title(),
                            c.company_status(),
                            c.date_of_creation(),
                            new TruProxyAddressDto(companyAddress.locality(), companyAddress.postal_code(), companyAddress.premises(), companyAddress.address_line_1(), companyAddress.country()),
                            officers.stream().map(officer -> {

                                Address officerAddress = addressRepository.findByOfficerId(officer.officer_id()).get();

                                return new TruProxyOfficerItemDto(officer.name(), officer.officer_role(), officer.appointed_on(), null, new TruProxyAddressDto(officerAddress.locality(), officerAddress.postal_code(), officerAddress.premises(), officerAddress.address_line_1(), officerAddress.country()));


                            }).toList()

                    ))
            );
        }


    }

}
