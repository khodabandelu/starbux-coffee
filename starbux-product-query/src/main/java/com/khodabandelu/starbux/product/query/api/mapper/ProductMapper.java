package com.khodabandelu.starbux.product.query.api.mapper;

import com.khodabandelu.starbux.product.query.api.dto.ProductDto;
import com.khodabandelu.starbux.product.query.domains.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    public static ProductDto toDto(Product product) {
//        return ProductDto.builder()
//                .id(product.getId().getId())
//                .name(product.getId().getName())
//                .categoryType(product.getId().getCategoryType())
//                .price(product.getPrice())
//                .creationDate(product.getCreationDate())
//                .build();
//
//    }


//    @Mapping(target = "id.id", source = "id")
//    @Mapping(target = "id.name", source = "name")
//    @Mapping(target = "id.categoryType", source = "categoryType")
    Product toEntity(ProductDto dto);

    @InheritInverseConfiguration
    ProductDto toDto(Product user);

    List<Product> toEntity(List<ProductDto> dtoList);

    List<ProductDto> toDto(List<Product> entityList);
}
