package com.investment.utils;

import com.investment.domain.entity.AbstractEntity_;
import com.investment.domain.entity.Investor;
import com.investment.domain.entity.Investor_;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(staticName = "instance")
public class InvestorSpecificationBuilder extends AbstractSpecBuilder<Investor> {

    public InvestorSpecificationBuilder withInvestorId(UUID id) {
        Optional.ofNullable(id).ifPresent(uuid -> root = root.and(withIdEquals(uuid)));
        return this;
    }

    public InvestorSpecificationBuilder withFirstName(String value) {
        Optional.ofNullable(value)
                .ifPresent(integer -> root = root.and(withStringValueLike(value, Investor_.firstName)));
        return this;
    }

    public InvestorSpecificationBuilder withLastName(String value) {
        Optional.ofNullable(value)
                .ifPresent(integer -> root = root.and(withStringValueLike(value, Investor_.lastName)));
        return this;
    }

    public InvestorSpecificationBuilder withEmailAddress(String value) {
        Optional.ofNullable(value)
                .ifPresent(integer -> root = root.and(withStringValueLike(value, Investor_.emailAddress)));
        return this;
    }

    public InvestorSpecificationBuilder withMobileNumber(String value) {
        Optional.ofNullable(value)
                .ifPresent(integer -> root = root.and(withStringValueLike(value, Investor_.mobileNumber)));
        return this;
    }

    static Specification<Investor> withIdEquals(UUID id) {
        return (root, cq, cb) -> cb.equal(root.get(AbstractEntity_.id), id);
    }

    static Specification<Investor> withFirstNameLike(String value) {
        return withStringValueLike(value, Investor_.firstName);
    }

    static Specification<Investor> withStringValueLike(String value, SingularAttribute<Investor, String> attribute) {
        return (root, query, cb) -> {
            String queryValue = "%" + StringUtils.lowerCase(value) + "%";
            Expression<String> expression = cb.lower(root.get(attribute));
            return cb.like(expression, queryValue);
        };
    }
}
