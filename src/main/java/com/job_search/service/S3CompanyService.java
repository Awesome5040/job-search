package com.job_search.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.job_search.repository.LogoRepository;
import com.job_search.repository.entity.Company;
import com.job_search.repository.entity.Logo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Profile("s3")
public class S3CompanyService extends CompanyService {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private LogoRepository logoRepository;

    private static final String COMPANY_BUCKET_NAME = "company-logos5040";

    public List<String> getAllLogoNames() {
        return s3.listObjects(new ListObjectsRequest().withBucketName(COMPANY_BUCKET_NAME))
                .getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .toList();
    }

    public S3Object getS3File(final String companyName) {
        Logo logo = logoRepository.findByCompanyId(companyRepository.findByName(companyName).getId());
        return s3.getObject(new GetObjectRequest(COMPANY_BUCKET_NAME, logo.getFileName()));
    }

    public String uploadFile(final String companyName, final String fileName, final MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3.putObject(COMPANY_BUCKET_NAME, fileName, file.getInputStream(), metadata);
        Company company = companyRepository.findByName(companyName);
        Logo logo;
        if (Objects.nonNull(company.getLogo()))
            logo = company.getLogo();
        else {
            logo = new Logo();
            logo.setCompany(companyRepository.findByName(companyName));
        }
        logo.setFileName(fileName);
        logoRepository.save(logo);
        return "File uploaded: " + fileName;
    }
}