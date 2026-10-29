package com.nexthire.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;

    private String location;

    private String industry;

    private String logoUrl;

    private String companySize;

    private boolean isVerified = true;

    private LocalDateTime createdAt;

    public Company() {
    }

    public Company(Long id, String name, String description, String website, String location, String industry, String logoUrl, String companySize, boolean isVerified, LocalDateTime createdAt) {
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
    }

    public static class CompanyBuilder {
        private Long id;
        private String name;
        private String description;
        private String website;
        private String location;
        private String industry;
        private String logoUrl;
        private String companySize;
        private boolean isVerified = true;
        private LocalDateTime createdAt;

        public CompanyBuilder id(Long id) { this.id = id; return this; }
        public CompanyBuilder name(String name) { this.name = name; return this; }
        public CompanyBuilder description(String description) { this.description = description; return this; }
        public CompanyBuilder website(String website) { this.website = website; return this; }
        public CompanyBuilder location(String location) { this.location = location; return this; }
        public CompanyBuilder industry(String industry) { this.industry = industry; return this; }
        public CompanyBuilder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
        public CompanyBuilder companySize(String companySize) { this.companySize = companySize; return this; }
        public CompanyBuilder isVerified(boolean isVerified) { this.isVerified = isVerified; return this; }
        public CompanyBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Company build() {
            return new Company(id, name, description, website, location, industry, logoUrl, companySize, isVerified, createdAt);
        }
    }
}
