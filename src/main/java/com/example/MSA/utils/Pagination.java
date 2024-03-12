package com.example.MSA.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination implements Pageable {

    private int limit;
    private int offset;
    private Sort sort;

	public Pagination(int limit, int offset, Sort sort) {
		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
	}

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new Pagination(getPageSize(), (int) (getOffset() + getPageSize()), sort);
    }

    public Pageable previous() {
        // The integers are positive. Subtracting does not let them become bigger than integer.
        return hasPrevious() ?
                new Pagination(getPageSize(), (int) (getOffset() - getPageSize()), sort): this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new Pagination(getPageSize(), 0, sort);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withPage'");
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

}
