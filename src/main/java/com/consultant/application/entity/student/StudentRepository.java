package com.consultant.application.entity.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByIdAndStudentStatus(String id, StudentStatus studentStatus);
}
