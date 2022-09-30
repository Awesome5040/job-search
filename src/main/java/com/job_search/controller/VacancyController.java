package com.job_search.controller;

import com.job_search.controller.dto.VacancyDto;
import com.job_search.service.CompanyService;
import com.job_search.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;


@RestController
public class VacancyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private VacancyService vacancyService;

    private static final String COMPANY_NOT_FOUND_MESSAGE = "The '%s' company not found";

    @PostMapping("api/companies/{company}/vacancies")
    public ResponseEntity<VacancyDto> createVacancy(@PathVariable(value = "company") String companyName, @RequestBody VacancyDto vacancyDto) {
        if (!companyService.existsByName(companyName))
            throw new EntityNotFoundException(format(COMPANY_NOT_FOUND_MESSAGE, companyName));
        return new ResponseEntity<>(vacancyService.createVacancy(vacancyDto, companyName), HttpStatus.CREATED);
    }

    @GetMapping("api/companies/{company}/vacancies")
    public ResponseEntity<List<VacancyDto>> getVacanciesByCompanyName(@PathVariable(value = "company") String companyName) {
        if (!companyService.existsByName(companyName))
            throw new EntityNotFoundException(format(COMPANY_NOT_FOUND_MESSAGE, companyName));
        return new ResponseEntity<>(vacancyService.getAllVacanciesByCompanyId(companyService.getIdByName(companyName)), HttpStatus.OK);
    }

    @GetMapping("api/vacancies")
    public ResponseEntity<List<VacancyDto>> getAllVacancies() {
        List<VacancyDto> vacancyDtos = vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancyDtos, HttpStatus.OK);
    }

    @DeleteMapping("api/vacancies/{id}")
    public ResponseEntity<HttpStatus> deleteVacancy(@PathVariable("id") long id) {
        if (!vacancyService.existsById(id))
            throw new EntityNotFoundException("Vacancy not found");
        vacancyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("api/vacancies/{id}")
    public ResponseEntity<VacancyDto> updateVacancy(@PathVariable("id") long id, @RequestBody VacancyDto vacancyDto) {
        if (!vacancyService.existsById(id))
            throw new EntityNotFoundException("Vacancy not found");
        return new ResponseEntity<>(vacancyService.updateVacancyById(id, vacancyDto), HttpStatus.OK);
    }

}

