package com.job_search.repository;

import com.google.common.collect.Iterators;
import com.job_search.repository.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.job_search.DataProvider.getCompany;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;


@DataJpaTest
class CompanyRepositoryIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testCompanyExistsByName() {
        Company company = getCompany();
        testEntityManager.persist(company);
        assertThat(companyRepository.existsByName(company.getName())).isTrue();
        assertThat(companyRepository.existsByName("Apple")).isTrue();
        assertThat(companyRepository.existsByName("Test")).isFalse();
    }

    @Test
    void testFindByName() {
        Company company = getCompany();
        testEntityManager.persist(company);
        Company findedCompany = companyRepository.findByName(company.getName());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(findedCompany.getName()).as("Company name is not as expected").isEqualTo(company.getName());
            softly.assertThat(findedCompany.getDescription()).as("Description is not as expected").isEqualTo(company.getDescription());
            softly.assertThat(findedCompany.getFounded()).as("Founded value is not as expected").isEqualTo(company.getFounded());
            softly.assertThat(findedCompany.getWebsite()).as("Website link is not as expected").isEqualTo(company.getWebsite());
            softly.assertThat(findedCompany.isWorkFromHome()).as("WFH value is not as expected").isEqualTo(company.isWorkFromHome());
        });
    }

    @Test
    void testDeleteByName() {
        Company company = getCompany();
        testEntityManager.persist(company);
        assertThat(Iterators.size((companyRepository.findAll().iterator()))).isEqualTo(2);
        companyRepository.deleteByName(company.getName());
        assertThat(Iterators.size((companyRepository.findAll().iterator()))).isEqualTo(1);
        companyRepository.deleteByName("Apple");
        assertThat(Iterators.size((companyRepository.findAll().iterator()))).isZero();
    }

}