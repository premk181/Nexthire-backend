package com.nexthire.backend.repository;

import com.nexthire.backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentProfileIdOrderByAppliedAtDesc(Long studentId);

    List<Application> findByJobIdOrderByAppliedAtDesc(Long jobId);

    Optional<Application> findByJobIdAndStudentProfileId(Long jobId, Long studentId);

    Boolean existsByJobIdAndStudentProfileId(Long jobId, Long studentId);

    Long countByJobId(Long jobId);
}
