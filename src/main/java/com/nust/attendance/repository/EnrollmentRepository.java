package com.nust.attendance.repository;
import com.nust.attendance.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_UserId(Long studentId);
    List<Enrollment> findByModule_ModuleId(Long moduleId);
    boolean existsByStudent_UserIdAndModule_ModuleId(Long studentId, Long moduleId);
}
