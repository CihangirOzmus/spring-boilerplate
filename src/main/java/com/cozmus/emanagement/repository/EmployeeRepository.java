package com.cozmus.emanagement.repository;

import com.cozmus.emanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    boolean existsById(Long id);
    void deleteById(Long id);
}
