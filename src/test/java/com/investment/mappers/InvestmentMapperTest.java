package com.investment.mappers;

import com.investment.domain.entity.Investment;
import com.investment.domain.entity.Product;
import com.investment.domain.valueobjects.ProductType;
import com.investment.model.InvestmentModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentMapperTest {

    InvestmentMapper investmentMapper = InvestmentMapper.INSTANCE;

    @Test
    void testWillMapInvestmentEntity_toInvestmentModel() {
        Investment investment = Investment.builder()
                .balance(BigDecimal.valueOf(569000))
                .id(UUID.randomUUID())
                .investorId(UUID.randomUUID())
                .product(Product.builder()
                        .id(UUID.randomUUID())
                        .productType(ProductType.RETIREMENT)
                        .id(UUID.randomUUID())
                        .build())
                .build();

        InvestmentModel toModel = investmentMapper.toModel(investment);

        assertNotNull(toModel);
        assertEquals(investment.getBalance(), toModel.getBalance());
        assertEquals(investment.getId(), toModel.getId());
        assertEquals(investment.getProductName(), toModel.getProduct().getProductName());
        assertEquals(investment.getProductType().name(), toModel.getProduct().getProductType().name());
        assertEquals(investment.getInvestorId(), toModel.getInvestorId());
    }

    @Test
    void testWillMapInvestmentModelToNull_whenEntityIsNull() {
        InvestmentModel toModel = investmentMapper.toModel(null);
        assertNull(toModel);
    }

    @Test
    void testWillMapInvestmentEntities_toListOfInvestmentModels() {
        Investment investment = Investment.builder()
                .balance(BigDecimal.valueOf(569000))
                .id(UUID.randomUUID())
                .investorId(UUID.randomUUID())
                .product(Product.builder()
                        .id(UUID.randomUUID())
                        .productType(ProductType.RETIREMENT)
                        .id(UUID.randomUUID())
                        .build())
                .build();

        List<InvestmentModel> toModels = investmentMapper.toModels(List.of(investment));

        assertNotNull(toModels);
        assertFalse(toModels.isEmpty());
    }

    @Test
    void testWillReturnNullValue_whenPassedEntityIsNull() {
        List<InvestmentModel> toModels = investmentMapper.toModels(null);
        assertNull(toModels);
    }

}