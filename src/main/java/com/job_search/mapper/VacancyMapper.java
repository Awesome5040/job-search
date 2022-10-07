package com.job_search.mapper;

import com.job_search.controller.dto.VacancyDto;
import com.job_search.repository.entity.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "isOpen", source = "open")
    Vacancy toVacancy(final VacancyDto vacancyDto);

    List<VacancyDto> toVacancies(final Iterable<Vacancy> companies);

    VacancyDto toVacancyDto(final Vacancy vacancy);
}
