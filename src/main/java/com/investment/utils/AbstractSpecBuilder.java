package com.investment.utils;

import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractSpecBuilder<T> {

    protected Specification<T> root;

    protected AbstractSpecBuilder() {
        this.root = root();
    }

    public Specification<T> build() {
        return root;
    }

    private Specification<T> root() {
        return ((aRoot, query, cb) -> cb.and());
    }
}
