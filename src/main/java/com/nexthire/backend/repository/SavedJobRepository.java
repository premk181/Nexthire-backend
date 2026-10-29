package com.nexthire.backend.repository;

import com.nexthire.backend.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByStudentProfileIdOrderBySavedAtDesc(Long studentId);

    Optional<SavedJob> findByStudentProfileIdAndJobId(Long studentId, Long jobId);

    Boolean existsByStudentProfileIdAndJobId(Long studentId, Long jobId);

    void deleteByStudentProfileIdAndJobId(Long studentId, Long jobId);
}
