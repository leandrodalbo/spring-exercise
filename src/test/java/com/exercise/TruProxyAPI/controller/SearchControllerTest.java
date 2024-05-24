package com.exercise.TruProxyAPI.controller;

import com.exercise.TruProxyAPI.dto.ProxySearchResult;
import com.exercise.TruProxyAPI.service.SearchService;
import com.exercise.TruProxyAPI.service.UpdateDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UpdateDataService updateDataService;

    @MockBean
    SearchService searchService;

    @Test
    public void shouldSearchLocally() throws Exception {

        given(searchService.localSearch(anyString())).willReturn(new ProxySearchResult(1, List.of()));

        MockHttpServletResponse res = mvc.perform(get("/api/abc123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(searchService, times(1)).localSearch(anyString());
    }


    @Test
    public void shouldSearchExternally() throws Exception {

        given(searchService.apiSearch(anyString(), anyBoolean())).willReturn(new ProxySearchResult(1, List.of()));

        MockHttpServletResponse res = mvc.perform(get("/api?companyNumber=ab4ws3")
                              .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(searchService, times(1)).apiSearch(anyString(), anyBoolean());
    }
}
