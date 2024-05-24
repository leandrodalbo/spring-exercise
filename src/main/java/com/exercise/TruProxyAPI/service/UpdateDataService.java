package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.dto.ProxySearchResultItem;
import com.exercise.TruProxyAPI.model.Address;
import com.exercise.TruProxyAPI.model.Company;
import com.exercise.TruProxyAPI.model.Officer;
import com.exercise.TruProxyAPI.repository.AddressRepository;
import com.exercise.TruProxyAPI.repository.CompanyRepository;
import com.exercise.TruProxyAPI.repository.OfficerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UpdateDataService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final OfficerRepository officerRepository;

    public UpdateDataService(CompanyRepository companyRepository, AddressRepository addressRepository, OfficerRepository officerRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.officerRepository = officerRepository;
    }

    public void updateCompaniesAndOfficers(List<ProxySearchResultItem> searchResults) {
        searchResults.forEach(item -> {

            try {

                Optional<Company> company = companyRepository.findByCompanyNumber(item.company_number());

                if (company.isEmpty()) {
                    saveNewCompanyData(item);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });

    }


    private void saveNewCompanyData(ProxySearchResultItem item) {
        Company newCompany = companyRepository.save(new Company(null, null, item.company_number(), item.company_type(), item.title(), item.company_status(), item.date_of_creation()));
        addressRepository.save(new Address(null, null, item.address().locality(), item.address().postal_code(), item.address().premises(), item.address().address_line_1(), item.address().country(), newCompany.company_id(), null));

        item.officers().forEach(officerItemDto -> {

            Officer newOfficer = officerRepository.save(
                    new Officer(null, null,
                            officerItemDto.name(), officerItemDto.officer_role(),
                            officerItemDto.appointed_on(),
                            newCompany.company_id()
                    ));

            addressRepository.save(new Address(null, null, officerItemDto.address().locality(), officerItemDto.address().postal_code(), officerItemDto.address().premises(), officerItemDto.address().address_line_1(), officerItemDto.address().country(), null, newOfficer.officer_id()));


        });

    }


}
