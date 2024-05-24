package com.exercise.TruProxyAPI.controller;


import com.exercise.TruProxyAPI.dto.ProxySearchResult;
import com.exercise.TruProxyAPI.service.SearchService;
import com.exercise.TruProxyAPI.service.UpdateDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final SearchService searchService;
    private final UpdateDataService updateDataService;

    public SearchController(SearchService searchService, UpdateDataService updateDataService) {
        this.searchService = searchService;
        this.updateDataService = updateDataService;
    }

    @GetMapping("/{companyId}")
    public ProxySearchResult localSearch(@PathVariable String companyId) {
        return searchService.localSearch(companyId);
    }

    @GetMapping
    public ProxySearchResult apiSearch(@RequestParam Optional<String> companyNumber, @RequestParam Optional<String> companyName, @RequestParam Optional<Boolean> active) {

        String company = (companyNumber.isPresent()) ? companyNumber.get() : (companyName.isPresent()) ? companyName.get() : null;

        if (company == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Search Input");
        }

        var result = searchService.apiSearch(company, active.isPresent() ? active.get() : false);
        updateDataService.updateCompaniesAndOfficers(result.items());
        return result;
    }

}
