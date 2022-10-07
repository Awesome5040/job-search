package com.job_search.service;

import com.job_search.controller.dto.CompanyDto;
import com.job_search.mapper.CompanyMapper;
import com.job_search.repository.entity.Company;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.job_search.DataProvider.getCompany;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CompanyServiceIntegrationTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void integrationTestCreateCompany() {
        CompanyDto companyDto = companyMapper.toCompanyDto(getCompany());
        CompanyDto createdCompanyDto = companyService.createCompany(companyDto);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdCompanyDto.getName()).as("Company name is not as expected").isEqualTo(companyDto.getName());
            softly.assertThat(createdCompanyDto.getDescription()).as("Description is not as expected").isEqualTo(companyDto.getDescription());
            softly.assertThat(createdCompanyDto.getFounded()).as("Founded value is not as expected").isEqualTo(companyDto.getFounded());
            softly.assertThat(createdCompanyDto.getWebsite()).as("Website link is not as expected").isEqualTo(companyDto.getWebsite());
            softly.assertThat(createdCompanyDto.isWorkFromHome()).as("WFH value is not as expected").isEqualTo(companyDto.isWorkFromHome());
        });
    }

    @Test
    void integrationTestExistsByName() {
        Company company = getCompany();
        testEntityManager.persist(company);
        assertThat(companyService.existsByName(company.getName())).isTrue();
        assertThat(companyService.existsByName("404 company")).isFalse();
    }

    @Test
    void integrationTestGetIdByName() {
        Company company = getCompany();
        company.setId(10L);
        testEntityManager.merge(company);
        assertThat(companyService.getIdByName(company.getName())).isEqualTo(10L);
    }

    @Test
    void integrationTestGetAllCompanies() {
        List<Company> companyList = Arrays.asList(getCompany(), getCompany(), getCompany(), getCompany());
        List<String> companyNames = companyList.stream().map(Company::getName).collect(Collectors.toList());
        companyNames.add("Apple"); //from data.sql
        companyList.forEach(company -> testEntityManager.persist(company));
        List<CompanyDto> companyDtoList = companyService.getAllCompanies();
        assertThat(companyNames.size()).isEqualTo(companyDtoList.size());
        assertThat(companyDtoList.stream().allMatch(c -> companyNames.contains(c.getName()))).isTrue();
    }

    @Test
    void integrationTestGetCompanyByName() {
        CompanyDto companyDto = companyService.getCompanyByName("Apple");
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(companyDto.getName()).as("Company name is not as expected").isEqualTo("Apple");
            softly.assertThat(companyDto.getDescription()).as("Description is not as expected").isEqualTo("Apple company");
            softly.assertThat(companyDto.getFounded()).as("Founded value is not as expected").isEqualTo(1999);
            softly.assertThat(companyDto.getWebsite()).as("Website link is not as expected").isEqualTo("apple.com");
            softly.assertThat(companyDto.isWorkFromHome()).as("WFH value is not as expected").isTrue();
        });
    }

    @Test
    void integrationTestRemoveCompany() {
        assertThat(companyService.getAllCompanies().size()).isEqualTo(1);
        companyService.removeCompany("Apple");
        assertThat(companyService.getAllCompanies().size()).isZero();
    }

    @Test
    void integrationTestUpdateCompanyByName() {
        Company company = getCompany();
        CompanyDto updatedCompanyDto = companyService.updateCompanyByName("Apple", companyMapper.toCompanyDto(company));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updatedCompanyDto.getName()).as("Company name is not as expected").isEqualTo(company.getName());
            softly.assertThat(updatedCompanyDto.getDescription()).as("Description is not as expected").isEqualTo(company.getDescription());
            softly.assertThat(updatedCompanyDto.getFounded()).as("Founded value is not as expected").isEqualTo(company.getFounded());
            softly.assertThat(updatedCompanyDto.getWebsite()).as("Website link is not as expected").isEqualTo(company.getWebsite());
            softly.assertThat(updatedCompanyDto.isWorkFromHome()).as("WFH value is not as expected").isEqualTo(company.isWorkFromHome());
        });
    }
}