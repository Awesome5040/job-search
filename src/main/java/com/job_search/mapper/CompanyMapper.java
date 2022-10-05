package com.job_search.mapper;

import com.job_search.controller.dto.CompanyDto;
import com.job_search.repository.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vacancyList", ignore = true)
    @Mapping(target = "isWorkFromHome", source = "workFromHome")
    Company toCompany(final CompanyDto companyDto);

    CompanyDto toCompanyDto(final Company company);

    List<CompanyDto> toCompanies(final Iterable<Company> companies);

}