package com.job_search.controller;


import com.amazonaws.services.s3.model.S3Object;

import com.job_search.service.S3CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Profile("s3")
@RestController
@RequestMapping("/api/v2/companies")
public class S3CompanyController extends CompanyController {

    @Autowired
    private S3CompanyService s3CompanyService;

    @GetMapping("/logo/all")
    public ResponseEntity<List<String>> getAllLogos() {
        return new ResponseEntity<>(s3CompanyService.getAllLogoNames(), HttpStatus.OK);
    }

    @GetMapping("{name}/download/logo")
    public ResponseEntity<byte[]> downloadLogo(@PathVariable("name") String companyName) throws IOException {
        if (!s3CompanyService.existsByName(companyName))
            throw new EntityNotFoundException(format("The '%s' company not found", companyName));
        S3Object object = s3CompanyService.getS3File(companyName);
        byte[] bytes = object.getObjectContent().readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(object.getObjectMetadata().getContentType()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=logo.png");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @PostMapping("{name}/logo/upload")
    public ResponseEntity<String> uploadCompanyLogo(@PathVariable("name") String companyName, @RequestParam("fileName") String fileName,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        if (!s3CompanyService.existsByName(companyName))
            throw new EntityNotFoundException(format("The '%s' company not found", companyName));

        return new ResponseEntity<>(s3CompanyService.uploadFile(companyName, fileName, file), HttpStatus.OK);
    }
}