package com.job_search.service;

import com.job_search.controller.dto.VacancyDto;
import com.job_search.mapper.VacancyMapper;
import com.job_search.repository.entity.Vacancy;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static com.job_search.DataProvider.getVacancy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class VacancyServiceIntegrationTest {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private VacancyMapper vacancyMapper;

    @Test
    void integrationTestCreateVacancy() {
        VacancyDto vacancyDto = vacancyMapper.toVacancyDto(getVacancy());
        VacancyDto createdVacancyDto = vacancyService.createVacancy(vacancyDto, "Apple");
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdVacancyDto.getTitle()).as("Company name is not as expected").isEqualTo(vacancyDto.getTitle());
            softly.assertThat(createdVacancyDto.getDescription()).as("Description is not as expected").isEqualTo(vacancyDto.getDescription());
            softly.assertThat(createdVacancyDto.isOpen()).as("IsOpen value is not as expected").isEqualTo(vacancyDto.isOpen());
            softly.assertThat(createdVacancyDto.getCompany().getName()).as("Company name is not as expected").isEqualTo("Apple");
        });
    }

    @Test
    void integrationTestGetAllVacanciesByCompanyId() {
        List<VacancyDto> vacancyDtoList = vacancyService.getAllVacanciesByCompanyId(10L);
        List<String> expectedVacancyTitles = Arrays.asList("QA1", "QA2");
        assertThat(vacancyDtoList.stream().allMatch(v -> expectedVacancyTitles.contains(v.getTitle()))).isTrue();
        assertThat(vacancyDtoList.size()).isEqualTo(expectedVacancyTitles.size());

    }

    @Test
    void integrationTestGetAllVacancies() {
        List<VacancyDto> vacancyDtoList = vacancyService.getAllVacancies();
        List<String> expectedVacancyTitles = Arrays.asList("QA1", "QA2");
        assertThat(vacancyDtoList.stream().allMatch(v -> expectedVacancyTitles.contains(v.getTitle()))).isTrue();
        assertThat(vacancyDtoList.size()).isEqualTo(expectedVacancyTitles.size());
    }

    @Test
    void integrationTestDeleteById() {
        assertThat(vacancyService.getAllVacancies().size()).isEqualTo(2);
        vacancyService.deleteById(1L);
        assertThat(vacancyService.getAllVacancies().size()).isEqualTo(1);
    }

    @Test
    void integrationTestExistsById() {
        assertThat(vacancyService.existsById(1L)).isTrue();
        assertThat(vacancyService.existsById(3L)).isFalse();
    }

    @Test
    void integrationTestUpdateVacancyById() {
        Vacancy vacancy = getVacancy();
        VacancyDto updatedVacancyDto = vacancyService.updateVacancyById(1L, vacancyMapper.toVacancyDto(vacancy));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updatedVacancyDto.getTitle()).as("Company name is not as expected").isEqualTo(vacancy.getTitle());
            softly.assertThat(updatedVacancyDto.getDescription()).as("Description is not as expected").isEqualTo(vacancy.getDescription());
            softly.assertThat(updatedVacancyDto.isOpen()).as("IsOpen value is not as expected").isEqualTo(vacancy.isOpen());
            softly.assertThat(updatedVacancyDto.getCompany().getName()).as("Company name is not as expected").isEqualTo("Apple");
        });
    }
}