package com.nexthire.backend.dto;

import java.time.LocalDateTime;

public class CompanyDto {

    private Long id;
    private String name;
    private String description;
    private String website;
    private String location;
    private String industry;
    private String logoUrl;
    private String companySize;
    private boolean isVerified;
    private LocalDateTime createdAt;

    public CompanyDto() {
    }

    public CompanyDto(Long id, String name, String description, String website, String location, String industry, String logoUrl, String companySize, boolean isVerified, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.location = location;
        this.industry = industry;
        this.logoUrl = logoUrl;
        this.companySize = companySize;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static CompanyDtoBuilder builder() {
        return new CompanyDtoBuilder();
    }

    public static class CompanyDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private String website;
        private String location;
        private String industry;
        private String logoUrl;
        private String companySize;
        private boolean isVerified;
        private LocalDateTime createdAt;

        public CompanyDtoBuilder id(Long id) { this.id = id; return this; }
        public CompanyDtoBuilder name(String name) { this.name = name; return this; }
        public CompanyDtoBuilder description(String description) { this.description = description; return this; }
        public CompanyDtoBuilder website(String website) { this.website = website; return this; }
        public CompanyDtoBuilder location(String location) { this.location = location; return this; }
        public CompanyDtoBuilder industry(String industry) { this.industry = industry; return this; }
        public CompanyDtoBuilder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
        public CompanyDtoBuilder companySize(String companySize) { this.companySize = companySize; return this; }
        public CompanyDtoBuilder isVerified(boolean isVerified) { this.isVerified = isVerified; return this; }
        public CompanyDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public CompanyDto build() {
            return new CompanyDto(id, name, description, website, location, industry, logoUrl, companySize, isVerified, createdAt);
        }
    }
}
