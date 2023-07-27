package com.investment.mappers;

import com.investment.domain.entity.Product;
import com.investment.model.ProductModel;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductModel toModel(Product product);
}
