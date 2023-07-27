package com.investment.mappers;

import com.investment.domain.entity.Investor;
import com.investment.model.InvestorModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InvestorMapper {

    InvestorMapper INSTANCE = Mappers.getMapper(InvestorMapper.class);

    @Mapping(target = "contacts.emailAddress", source = "emailAddress")
    @Mapping(target = "contacts.mobileNumber", source = "mobileNumber")
    InvestorModel toModel(Investor investor);

    @Mapping(target = "contacts.emailAddress", source = "emailAddress")
    @Mapping(target = "contacts.mobileNumber", source = "mobileNumber")
    List<InvestorModel> toModels(List<Investor> investors);

}
