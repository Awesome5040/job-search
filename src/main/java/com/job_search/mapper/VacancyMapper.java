package com.job_search.mapper;

import com.job_search.controller.dto.VacancyDto;
import com.job_search.repository.entity.Vacancy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    Vacancy toVacancy(final VacancyDto vacancyDto);

    List<VacancyDto> toVacancies(final Iterable<Vacancy> companies);
}
