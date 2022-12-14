package com.lumimindsinc.domain_name.usersregistration.user.registration.repositories;

import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);

    Employee getByEmail(String email);

    Employee getById(Long employeeId);

    Page<Employee> findAllByIsActiveOrderByIdDesc(Boolean isActive, Pageable paging);

    Employee findByEmailIgnoreCase(String email);
}
