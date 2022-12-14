package com.lumimindsinc.domain_name.usersregistration.user.registration.service.impl;

import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Roles;
import com.lumimindsinc.domain_name.usersregistration.user.registration.repositories.RoleRepository;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Roles get(Long id) throws RecordNotFoundException {
        Roles roles = roleRepository.findOneById(id);
        if (roles == null)
            throw new RecordNotFoundException("Role not found with this Id: " + id);
        else
            return roles;
    }

    @Override
    public List<Roles> getAll(Principal principal) throws RecordNotFoundException {
        List<Roles> roles = roleRepository.findAll();
        if (roles == null || roles.isEmpty())
            throw new RecordNotFoundException("No roles found!");
        else
            return roles;
    }

    @Override
    public boolean existsById(Long ids) {
        Boolean role = roleRepository.existsById(ids);
        return role;
    }

    @Override
    public Roles getByType(String type) {
       Roles role = roleRepository.getByType(type);
        return role;
    }

}
