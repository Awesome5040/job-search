package com.job_search.controller;

import com.job_search.controller.dto.VacancyDto;
import com.job_search.service.CompanyService;
import com.job_search.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.String.format;


@RestController
public class VacancyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private VacancyService vacancyService;

    private static final String COMPANY_NOT_CREATED_MESSAGE = "The '%s' company not created";

    @PostMapping("api/companies/{company}/vacancies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createVacancy(@PathVariable(value = "company") String companyName, @RequestBody VacancyDto vacancyDto) {
        if (!companyService.existsByName(companyName))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(format(COMPANY_NOT_CREATED_MESSAGE, companyName));
        vacancyService.createVacancy(vacancyDto, companyName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(format("The '%s' vacancy was created", vacancyDto.getTitle()));
    }

    @GetMapping("api/companies/{company}/vacancies")
    public List<VacancyDto> getVacanciesByCompanyName(@PathVariable(value = "company") String companyName) {
        if (!companyService.existsByName(companyName))
            throw new ResourceNotFoundException(format(COMPANY_NOT_CREATED_MESSAGE, companyName));
        return vacancyService.getAllVacanciesByCompanyId(companyService.getIdByName(companyName));
    }

    @GetMapping("api/vacancies")
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @DeleteMapping("api/vacancies/{id}")
    public ResponseEntity deleteVacancy(@PathVariable("id") long id) {
        if (!vacancyService.existsById(id))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Vacancy not found");
        vacancyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("api/vacancies/{id}")
    public ResponseEntity updateVacancy(@PathVariable("id") long id, @RequestBody VacancyDto vacancyDto) {
        if (!vacancyService.existsById(id))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Vacancy not found");
        vacancyService.updateVacancyById(id, vacancyDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("The vacancy was updated");
    }

}

