package com.job_search.service;

import com.job_search.DataProvider;
import com.job_search.controller.dto.VacancyDto;
import com.job_search.mapper.VacancyMapper;
import com.job_search.repository.CompanyRepository;
import com.job_search.repository.VacancyRepository;
import com.job_search.repository.entity.Company;
import com.job_search.repository.entity.Vacancy;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.job_search.DataProvider.getVacancy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class VacancyServiceUnitTest {

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private VacancyService vacancyService;

    @Spy
    private VacancyMapper vacancyMapper = Mappers.getMapper(VacancyMapper.class);

    @Test
    void testCreateVacancy() {
        Vacancy vacancy = DataProvider.getVacancy();
        Company company = DataProvider.getCompany();
        when(companyRepository.findByName(company.getName())).thenReturn(company);
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(vacancy);
        VacancyDto newVacancy = vacancyService.createVacancy(vacancyMapper.toVacancyDto(vacancy), company.getName());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(newVacancy.getTitle()).as("Company name is not as expected").isEqualTo(vacancy.getTitle());
            softly.assertThat(newVacancy.getDescription()).as("Description is not as expected").isEqualTo(vacancy.getDescription());
            softly.assertThat(newVacancy.isOpen()).as("IsOpen value is not as expected").isEqualTo(vacancy.isOpen());
            softly.assertThat(newVacancy.getCompany().getName()).as("Company name is not as expected").isEqualTo(company.getName());
        });
        verify(companyRepository, times(1)).findByName(company.getName());
        verify(vacancyRepository, times(1)).save(any(Vacancy.class));
    }

    @Test
    void testGetAllVacanciesByCompanyId() {
        Company company = new Company();
        company.setId(10L);
        List<Vacancy> vacancyList = Arrays.asList(getVacancy(), getVacancy(), getVacancy());
        vacancyList.forEach(v -> v.setCompany(company));
        when(vacancyRepository.findAllByCompanyId(company.getId())).thenReturn(vacancyList);
        assertThat(vacancyService.getAllVacanciesByCompanyId(company.getId()).size()).isEqualTo(vacancyList.size());
        verify(vacancyRepository, times(1)).findAllByCompanyId(company.getId());
    }

    @Test
    void testGetAllVacancies() {
        List<Vacancy> vacancyList = Arrays.asList(getVacancy(), getVacancy(), getVacancy());
        when(vacancyRepository.findAll()).thenReturn(vacancyList);
        assertThat(vacancyService.getAllVacancies().size()).isEqualTo(vacancyList.size());
        verify(vacancyRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        Vacancy vacancy = DataProvider.getVacancy();
        vacancy.setId(10L);
        vacancyService.deleteById(vacancy.getId());
        verify(vacancyRepository, times(1)).deleteById(vacancy.getId());
    }

    @Test
    void testExistsById() {
        Vacancy vacancy = DataProvider.getVacancy();
        vacancy.setId(5L);
        when(vacancyRepository.existsById(vacancy.getId())).thenReturn(true);
        assertThat(vacancyService.existsById(vacancy.getId())).isTrue();
        verify(vacancyRepository, times(1)).existsById(vacancy.getId());
    }

    @Test
    void testUpdateVacancyById() {
        Vacancy vacancy = DataProvider.getVacancy();
        vacancy.setId(5L);
        Vacancy newVacancy = DataProvider.getVacancy();
        VacancyDto newVacancyDto = vacancyMapper.toVacancyDto(newVacancy);
        when(vacancyRepository.findById(vacancy.getId())).thenReturn(Optional.of(vacancy));
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(newVacancy);
        assertThat(vacancyService.updateVacancyById(vacancy.getId(), newVacancyDto).getTitle()).isEqualTo(newVacancy.getTitle());
        verify(vacancyRepository, times(1)).findById(vacancy.getId());
        verify(vacancyRepository, times(1)).save(any(Vacancy.class));
    }
}