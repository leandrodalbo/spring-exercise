package com.exercise.TruProxyAPI.controller;


import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyItemDto;
import com.exercise.TruProxyAPI.service.TruProxyCallService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lookup")
public class LookupController {

    private final TruProxyCallService proxyCallService;

    public LookupController(TruProxyCallService proxyCallService) {
        this.proxyCallService = proxyCallService;
    }

    @GetMapping("/test")
    public List<TruProxyCompanyItemDto> test() {
        return proxyCallService.findCompanies("BBC LIMITED");
    }
}
