package com.job_search.repository;

import com.job_search.repository.entity.Vacancy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class VacancyRepositoryIntegrationTest {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Test
    void testFindAllByCompanyId() {
        List<Vacancy> vacancyList = vacancyRepository.findAllByCompanyId(10L);
        assertThat(vacancyList.size()).isEqualTo(2);
        assertThat(vacancyList.get(0).getId()).isEqualTo(1L);
        assertThat(vacancyList.get(1).getId()).isEqualTo(2L);
        assertThat(vacancyList.get(0).getTitle()).isEqualTo("QA1");
        assertThat(vacancyList.get(1).getTitle()).isEqualTo("QA2");
    }

    @Test
    void testExistsById() {
        assertThat(vacancyRepository.existsById(1L)).isTrue();
        assertThat(vacancyRepository.existsById(2L)).isTrue();
        assertThat(vacancyRepository.existsById(3L)).isFalse();
    }

}