package com.lumimindsinc.domain_name.usersregistration.user.registration.service;


import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Roles;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;


@Service
public interface RoleService {

    Roles get(Long id) throws RecordNotFoundException;

    List<Roles> getAll(Principal principal) throws RecordNotFoundException;

    boolean existsById(Long ids);

    Roles getByType(String super_admin);
}
