package com.job_search.repository;

import com.job_search.repository.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

    boolean existsByName(final String name);

    Company findByName(final String name);

    @Transactional
    void deleteByName(final String name);

}
