package com.job_search.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Company name is mandatory")
    @Size(min = 5, max = 20)
    private String name;
    @NotBlank(message = "Company description is mandatory")
    private String description;
    @NotNull(message = "Company founded is mandatory")
    @Range(min = 1900, max = 2022)
    private int founded;
    @NotBlank(message = "Company website is mandatory")
    private String website;
    private boolean isWorkFromHome;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<Vacancy> vacancyList;
}
