package com.investment.mappers;

import com.investment.domain.entity.Investor;
import com.investment.domain.entity.Investment;
import com.investment.domain.valueobjects.ProductType;
import com.investment.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper
public interface InvestorMapper {

    InvestorMapper INSTANCE = Mappers.getMapper(InvestorMapper.class);

    @Mapping(source = "contacts.emailAddress", target = "emailAddress")
    @Mapping(source = "contacts.mobileNumber", target = "mobileNumber")
    Investor toEntity(InvestorModel model);

    @Mapping(target = "contacts.emailAddress", source = "emailAddress")
    @Mapping(target = "contacts.mobileNumber", source = "mobileNumber")
    InvestorModel toModel(Investor investor);

    @Mapping(target = "contacts.emailAddress", source = "emailAddress")
    @Mapping(target = "contacts.mobileNumber", source = "mobileNumber")
    List<InvestorModel> toModels(List<Investor> investors);

    default InvestmentModel toModel(Investment entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return InvestmentModel.builder()
                .balance(entity.getBalance())
                .id(entity.getId())
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

    List<InvestmentModel> toModel(List<Investment> products);

}
