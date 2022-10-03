package com.job_search.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Vacancy {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Vacancy title is mandatory")
    private String title;
    @NotBlank(message = "Vacancy description is mandatory")
    private String description;
    private boolean isOpen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
