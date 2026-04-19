package com.nust.attendance.repository;

import com.nust.attendance.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentNumber(String studentNumber);
    boolean existsByStudentNumber(String studentNumber);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.role r WHERE " +
           "(:search IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%',:search,'%')) OR u.studentNumber LIKE CONCAT('%',:search,'%')) " +
           "AND (:role IS NULL OR r.roleName = :role)")
    Page<User> searchUsers(@Param("search") String search, @Param("role") String role, Pageable pageable);
}
