package com.job_search.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class Logo {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    private String fileName;

}
