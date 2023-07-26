package com.investment.model.page;

import com.investment.domain.entity.Investor;
import com.investment.mappers.InvestorMapper;
import com.investment.model.InvestorModel;
import org.springframework.data.domain.Page;

import java.util.List;

public class InvestorsPage extends AbstractPage<InvestorModel> {

    private InvestorsPage(List<InvestorModel> content,
                          Boolean empty,
                          Boolean first,
                          Boolean last,
                          Integer number,
                          Integer size, Long totalElements,
                          Integer totalPages) {
        super(content, empty, first, last, number, size, totalElements, totalPages);
    }

    public static InvestorsPage toPage(Page<Investor> page) {
        return new InvestorsPage(InvestorMapper.INSTANCE.toModels(page.getContent()), page.isEmpty(),
                page.isFirst(), page.isLast(),
                page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

}

