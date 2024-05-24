package com.exercise.TruProxyAPI.dto;

import java.util.List;

public record ProxySearchResult(
        Integer total_results,
        List<ProxySearchResultItem> items
) {
}
