package com.job_search.repository;

import com.job_search.repository.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {

    List<Vacancy> findAllByCompanyId(final Long id);

    boolean existsById(final Long id);

}
