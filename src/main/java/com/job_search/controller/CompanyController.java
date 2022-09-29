package com.job_search.controller;

import com.job_search.controller.dto.CompanyDto;
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

import java.util.List;

import static java.lang.String.format;


@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company")
    public CompanyDto getCompanyByName(@RequestParam String name) {
        return companyService.getCompanyByName(name);
    }

    @PostMapping
    public ResponseEntity createCompany(@RequestBody CompanyDto companyDto) {
        if (companyService.existsByName(companyDto.getName()))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(format("The '%s' company already exist", companyDto.getName()));
        companyService.createCompany(companyDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(format("The '%s' company was created", companyDto.getName()));
    }

    @DeleteMapping("/company/{name}")
    public ResponseEntity deleteCompany(@PathVariable("name") String name) {
        if (!companyService.existsByName(name))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(format("The '%s' company has not yet been created", name));
        companyService.removeCompany(name);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(format("The '%s' company was deleted", name));
    }

    @PutMapping("/company/{name}")
    public ResponseEntity updateCompany(@PathVariable("name") String name, @RequestBody CompanyDto companyDto) {
        if (!companyService.existsByName(name))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(format("The '%s' company has not yet been created", name));
        companyService.updateCompanyByName(name, companyDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("The company was updated");
    }
}
