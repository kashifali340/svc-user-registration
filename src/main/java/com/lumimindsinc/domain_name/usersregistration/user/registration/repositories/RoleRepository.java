package com.lumimindsinc.domain_name.usersregistration.user.registration.repositories;

import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findOneById(Long id);
   // Roles findOneByRole (String role);
    Roles getByType(String type);

    Roles findOneByType(String hiringManagerRole);
}
