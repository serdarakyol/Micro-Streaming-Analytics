package com.example.MSA.utils;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@AutoConfiguration
public class HeaderResponse {

    private final String url;

    public HttpHeaders getHeadersResponse(Filter filter, Integer totalItems, String endpoint) {
        long nextItems = -1;
        if (filter.getPageable().isPaged()) {
            long currentOffset = filter.getPageable().getOffset();
            int itemPerPage = filter.getPageable().getPageSize();
            long cumulativeItemNumber = (currentOffset) + itemPerPage;
            nextItems = (currentOffset + itemPerPage < totalItems) ? currentOffset + itemPerPage : -1;
            // no need link if it's first page and fit all items in a page
            if (currentOffset == 0 && cumulativeItemNumber >= totalItems) {
                nextItems = -1;
            }
            // no need link if it's last page and fit all items in a page
            if (cumulativeItemNumber >= totalItems) {
                nextItems = -1;
            }
        }
        // Create Link header
        String linkHeader = buildLinkHeader(filter, nextItems, endpoint);

        // Set headers in the response
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", linkHeader);
        responseHeaders.add("X-Total-Count", String.valueOf(totalItems));
        responseHeaders.add("X-Limit", String.valueOf(
                filter.getPageable().isPaged() ? filter.getPageable().getPageSize() : String.valueOf(totalItems)));

        return responseHeaders;
    }

    private String buildLinkHeader(Filter filter, long nextItems, String endpoint) {
        StringBuilder linkHeader = new StringBuilder();
        if (nextItems != -1) {
            long nextOffset = filter.getPageable().getOffset() + filter.getPageable().getPageSize(); // Calculate next offset
            String nextPageUrl = this.buildNextPageUrl(filter, endpoint, nextOffset);
            linkHeader.append("<").append(nextPageUrl).append(">; rel=\"next\"");
        }
        return linkHeader.toString();
    }

    private String buildNextPageUrl(Filter filter, String endpoint, long nextOffset) {
        StringBuilder sb = new StringBuilder(url);
        sb.append(endpoint);
        sb.append("?startDate=");
        sb.append(filter.getStartDate().toString());
        sb.append("&endDate=");
        sb.append(filter.getEndDate().toString());
        sb.append("&offset=");
        sb.append(filter.getPageable().next().getOffset());
        sb.append("&limit=");
        sb.append(filter.getLimit());
        return sb.toString();
    }
}

