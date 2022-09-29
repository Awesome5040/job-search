package com.job_search.controller.dto;

import lombok.Data;

@Data
public class VacancyDto {

    private String title;
    private String description;
    private boolean isOpen;
    private CompanyDto company;

}
