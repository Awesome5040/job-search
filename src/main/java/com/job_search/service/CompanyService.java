package com.job_search.service;

import com.job_search.controller.dto.CompanyDto;
import com.job_search.mapper.CompanyMapper;
import com.job_search.repository.CompanyRepository;
import com.job_search.repository.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    public CompanyDto createCompany(final CompanyDto companyDto) {
        Company company = companyRepository.save(companyMapper.toCompany(companyDto));
        return companyMapper.toCompanyDto(company);
    }

    public boolean existsByName(final String value) {
        return companyRepository.existsByName(value);
    }

    public Long getIdByName(final String value) {
        return companyRepository.findByName(value).getId();
    }

    public List<CompanyDto> getAllCompanies() {
        return companyMapper.toCompanies(companyRepository.findAll());
    }

    public CompanyDto getCompanyByName(final String name) {
        return companyMapper.toCompanyDto(companyRepository.findByName(name));
    }

    public void removeCompany(final String name) {
        companyRepository.deleteByName(name);
    }

    public CompanyDto updateCompanyByName(final String name, final CompanyDto companyDto) {
        Company company = companyRepository.findByName(name);
        company.setName(companyDto.getName());
        company.setWebsite(companyDto.getWebsite());
        company.setDescription(companyDto.getDescription());
        company.setWorkFromHome(companyDto.isWorkFromHome());
        company.setFounded(companyDto.getFounded());
        companyRepository.save(company);
        return companyMapper.toCompanyDto(company);
    }

}
