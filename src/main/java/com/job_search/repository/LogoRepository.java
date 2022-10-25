package com.job_search.repository;

import com.job_search.repository.entity.Logo;
import org.springframework.data.repository.CrudRepository;

public interface LogoRepository extends CrudRepository<Logo, Long> {

    Logo findByCompanyId(final Long id);

}
