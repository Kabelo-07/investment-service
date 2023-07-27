package com.investment.mappers;

import com.investment.domain.entity.Investment;
import com.investment.domain.valueobjects.ProductType;
import com.investment.model.InvestmentModel;
import com.investment.model.ProductModel;
import com.investment.model.ProductTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper
public interface InvestmentMapper {

    InvestmentMapper INSTANCE = Mappers.getMapper(InvestmentMapper.class);

    default InvestmentModel toModel(Investment entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return InvestmentModel.builder()
                .balance(entity.getBalance())
                .id(entity.getId())
                .investorId(entity.getInvestorId())
                .product(ProductModel.builder()
                        .productName(entity.getProductName())
                        .productType(map(entity.getProductType()))
                        .build())
                .build();
    }

    default ProductTypeModel map(ProductType productType) {
        return switch (productType) {
            case SAVINGS -> ProductTypeModel.SAVINGS;
            case RETIREMENT -> ProductTypeModel.RETIREMENT;
        };
    }

    List<InvestmentModel> toModels(List<Investment> products);

}
