package com.job_search.service;

import com.job_search.DataProvider;
import com.job_search.controller.dto.CompanyDto;
import com.job_search.mapper.CompanyMapper;
import com.job_search.repository.CompanyRepository;
import com.job_search.repository.entity.Company;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.job_search.DataProvider.getCompany;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceUnitTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Spy
    private CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompany() {
        Company company = DataProvider.getCompany();
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        CompanyDto newCompany = companyService.createCompany(companyMapper.toCompanyDto(company));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(newCompany.getName()).as("Company name is not as expected").isEqualTo(company.getName());
            softly.assertThat(newCompany.getDescription()).as("Description is not as expected").isEqualTo(company.getDescription());
            softly.assertThat(newCompany.getFounded()).as("Founded value is not as expected").isEqualTo(company.getFounded());
            softly.assertThat(newCompany.getWebsite()).as("Website link is not as expected").isEqualTo(company.getWebsite());
            softly.assertThat(newCompany.isWorkFromHome()).as("WFH value is not as expected").isEqualTo(company.isWorkFromHome());
        });
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void testExistsByName() {
        Company company = DataProvider.getCompany();
        when(companyRepository.existsByName(company.getName())).thenReturn(true);
        assertThat(companyService.existsByName(company.getName())).isTrue();
        verify(companyRepository, times(1)).existsByName(company.getName());
    }

    @Test
    void testGetIdByName() {
        Company company = DataProvider.getCompany();
        company.setId(100L);
        when(companyRepository.findByName(company.getName())).thenReturn(company);
        assertThat(companyService.getIdByName(company.getName())).isEqualTo(100L);
        verify(companyRepository, times(1)).findByName(company.getName());

    }

    @Test
    void testGetAllCompanies() {
        List<Company> companyList = Arrays.asList(getCompany(), getCompany(), getCompany(), getCompany());
        when(companyRepository.findAll()).thenReturn(companyList);
        assertThat(companyService.getAllCompanies().size()).isEqualTo(companyList.size());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testGetCompanyByName() {
        Company company = DataProvider.getCompany();
        when(companyRepository.findByName(company.getName())).thenReturn(company);
        assertThat(companyService.getCompanyByName(company.getName()).getName()).isEqualTo(company.getName());
        verify(companyRepository, times(1)).findByName(company.getName());
    }

    @Test
    void testRemoveCompany() {
        Company company = DataProvider.getCompany();
        companyService.removeCompany(company.getName());
        verify(companyRepository, times(1)).deleteByName(company.getName());
    }

    @Test
    void testUpdateCompanyByName() {
        Company company = DataProvider.getCompany();
        company.setId(100L);
        Company newCompany = DataProvider.getCompany();
        CompanyDto newCompanyDto = companyMapper.toCompanyDto(newCompany);
        when(companyRepository.findByName(company.getName())).thenReturn(company);
        when(companyRepository.save(any(Company.class))).thenReturn(newCompany);
        assertThat(companyService.updateCompanyByName(company.getName(), newCompanyDto).getName()).isEqualTo(newCompany.getName());
        verify(companyRepository, times(1)).findByName(any(String.class));
        verify(companyRepository, times(1)).save(any(Company.class));
    }
}