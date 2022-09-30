package com.job_search.controller;

import com.job_search.controller.dto.CompanyDto;
import com.job_search.exception.DuplicateRecordException;
import com.job_search.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;


@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/company")
    public ResponseEntity<CompanyDto> getCompanyByName(@RequestParam String name) {
        return new ResponseEntity<>(companyService.getCompanyByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        if (companyService.existsByName(companyDto.getName()))
            throw new DuplicateRecordException(format("The '%s' company already exist", companyDto.getName()));
        return new ResponseEntity<>(companyService.createCompany(companyDto), HttpStatus.OK);
    }

    @DeleteMapping("/company/{name}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable("name") String name) {
        if (!companyService.existsByName(name))
            throw new EntityNotFoundException(format("The '%s' company not found", name));
        companyService.removeCompany(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/company/{name}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable("name") String name, @RequestBody CompanyDto companyDto) {
        if (!companyService.existsByName(name))
            throw new EntityNotFoundException(format("The '%s' company not found", name));
        return new ResponseEntity<>(companyService.updateCompanyByName(name, companyDto), HttpStatus.OK);
    }
}
