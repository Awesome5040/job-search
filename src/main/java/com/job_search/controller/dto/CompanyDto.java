package com.job_search.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {

    private String name;
    private String description;
    private int founded;
    private String website;
    private boolean isWorkFromHome;

}
