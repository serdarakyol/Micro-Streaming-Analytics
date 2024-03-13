package com.example.MSA.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Filter {
    private Integer offset;
    private Integer limit;
    private Instant startDate;
    private Instant endDate;
    private Pageable pageable;

    public Filter(String startDate, String endDate, Integer offset, Integer limit) {
        this.loadPageable(offset, limit);
        this.loadDate(startDate, endDate);
    }

    private void loadPageable(Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Order.asc("createAt"));
        if (offset != null && limit != null) {
            limit = limit > 1000 ? 1000 : limit;
            this.limit = limit;
            this.offset = offset;
            this.pageable = new Pagination(limit, offset, sort);
        } else if (offset != null && limit == null) {
            this.limit = 100;
            this.offset = offset;
            this.pageable = new Pagination(this.limit, offset, sort);
        } else if (offset == null && limit != null) {
            this.offset = 0;
            this.limit = limit;
            limit = limit > 1000 ? 1000 : limit;
            this.pageable = new Pagination(limit, this.offset, sort);
        } else {
            this.limit = 100;
            this.offset = 0;
            this.pageable = new Pagination(this.limit, this.offset, sort);
        }
    }

    private void loadDate(String startDate, String endDate) {
        try {
            this.endDate = endDate != null ? Instant.parse(endDate) : Instant.now();
            this.startDate = startDate != null ? Instant.parse(startDate) : this.endDate.minus(30, ChronoUnit.MINUTES);
        } catch (Exception e) {
            log.error("Pagination String dates could not conver to Instant: ".concat(e.toString()));
        }
    }
}
