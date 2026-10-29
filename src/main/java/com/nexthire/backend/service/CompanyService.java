package com.nexthire.backend.service;

import com.nexthire.backend.dto.CompanyDto;
import com.nexthire.backend.entity.Company;
import com.nexthire.backend.entity.RecruiterProfile;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.repository.CompanyRepository;
import com.nexthire.backend.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    public CompanyService(CompanyRepository companyRepository,
                          RecruiterProfileRepository recruiterProfileRepository) {
        this.companyRepository = companyRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
        return mapToDto(company);
    }

    @Transactional
    public CompanyDto createCompanyForRecruiter(String recruiterEmail, CompanyDto companyDto) {
        Company company = Company.builder()
                .name(companyDto.getName())
                .description(companyDto.getDescription())
                .website(companyDto.getWebsite())
                .location(companyDto.getLocation())
                .industry(companyDto.getIndustry())
                .logoUrl(companyDto.getLogoUrl())
                .companySize(companyDto.getCompanySize())
                .isVerified(true)
                .build();

        company = companyRepository.save(company);

        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserEmail(recruiterEmail)
                .orElse(null);
        if (recruiterProfile != null) {
            recruiterProfile.setCompany(company);
            recruiterProfileRepository.save(recruiterProfile);
        }

        return mapToDto(company);
    }

    @Transactional
    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

        if (companyDto.getName() != null) company.setName(companyDto.getName());
        if (companyDto.getDescription() != null) company.setDescription(companyDto.getDescription());
        if (companyDto.getWebsite() != null) company.setWebsite(companyDto.getWebsite());
        if (companyDto.getLocation() != null) company.setLocation(companyDto.getLocation());
        if (companyDto.getIndustry() != null) company.setIndustry(companyDto.getIndustry());
        if (companyDto.getLogoUrl() != null) company.setLogoUrl(companyDto.getLogoUrl());
        if (companyDto.getCompanySize() != null) company.setCompanySize(companyDto.getCompanySize());

        company = companyRepository.save(company);
        return mapToDto(company);
    }

    public CompanyDto mapToDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .website(company.getWebsite())
                .location(company.getLocation())
                .industry(company.getIndustry())
                .logoUrl(company.getLogoUrl())
                .companySize(company.getCompanySize())
                .isVerified(company.isVerified())
                .createdAt(company.getCreatedAt())
                .build();
    }
}
