package com.investment.mappers;

import com.investment.domain.entity.Product;
import com.investment.model.ProductModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product toEntity(ProductModel model);

    ProductModel toModel(Product product);

    List<ProductModel> toModel(List<Product> products);
}
