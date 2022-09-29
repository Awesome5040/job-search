package com.job_search.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private int founded;
    private String website;
    private boolean isWorkFromHome;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<Vacancy> vacancyList;
}
