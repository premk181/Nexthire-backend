package com.nexthire.backend.repository;

import com.nexthire.backend.entity.ExperienceLevel;
import com.nexthire.backend.entity.Job;
import com.nexthire.backend.entity.JobStatus;
import com.nexthire.backend.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiterId(Long recruiterId);

    List<Job> findByCompanyId(Long companyId);

    List<Job> findByStatus(JobStatus status);

    @Query("SELECT j FROM Job j WHERE j.status = 'ACTIVE' " +
           "AND (:search IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(j.company.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(j.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:jobType IS NULL OR j.jobType = :jobType) " +
           "AND (:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel) " +
           "ORDER BY j.createdAt DESC")
    List<Job> searchJobs(
            @Param("search") String search,
            @Param("location") String location,
            @Param("jobType") JobType jobType,
            @Param("experienceLevel") ExperienceLevel experienceLevel
    );
}
