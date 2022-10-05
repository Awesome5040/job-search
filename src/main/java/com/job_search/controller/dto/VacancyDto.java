package com.job_search.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacancyDto {

    private String title;
    private String description;
    private boolean isOpen;
    private CompanyDto company;

}
