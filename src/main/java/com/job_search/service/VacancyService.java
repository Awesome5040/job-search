package com.job_search.service;

import com.job_search.controller.dto.VacancyDto;
import com.job_search.mapper.VacancyMapper;
import com.job_search.repository.CompanyRepository;
import com.job_search.repository.VacancyRepository;
import com.job_search.repository.entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private VacancyMapper vacancyMapper;

    @Autowired
    private CompanyRepository companyRepository;

    public void createVacancy(final VacancyDto vacancyDto, final String companyName) {
        Vacancy vacancy = vacancyMapper.toVacancy(vacancyDto);
        vacancy.setCompany(companyRepository.findByName(companyName));
        vacancyRepository.save(vacancy);
    }

    public List<VacancyDto> getAllVacanciesByCompanyId(final Long companyId) {
        return vacancyMapper.toVacancies(vacancyRepository.findAllByCompanyId(companyId));
    }

    public List<VacancyDto> getAllVacancies() {
        return vacancyMapper.toVacancies(vacancyRepository.findAll());
    }

    public void deleteById(final long id) {
        vacancyRepository.deleteById(id);
    }

    public boolean existsById(final long id) {
        return vacancyRepository.existsById(id);
    }

    public void updateVacancyById(final long id, final VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new RuntimeException("No data"));
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setOpen(vacancyDto.isOpen());
        vacancy.setTitle(vacancyDto.getTitle());
        vacancyRepository.save(vacancy);
    }
}
