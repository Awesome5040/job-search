package com.job_search.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacancyDto {

    private String title;
    private String description;
    @JsonProperty("isOpen")
    private boolean isOpen;
    @JsonInclude(JsonInclude.Include. NON_NULL)
    private CompanyDto company;

}
