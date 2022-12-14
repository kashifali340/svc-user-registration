package com.lumimindsinc.domain_name.usersregistration.user.registration.mapper;


import com.lumimindsinc.domain_name.usersregistration.user.registration.dto.EmployeeDto;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.RoleService;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Roles;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import com.lumimindsinc.domain_name.usersregistration.utill.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmployeeMapper {

    @Autowired
     PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    String passwordFormat = Constants.getAlphaNumericString(16);
    //String password = passwordEncoder.encode(passwordFormat);


    public Employee mapDtoToModel(EmployeeDto employeeDto) throws RecordNotFoundException {
        Roles role = new Roles();
        if (!Objects.isNull(employeeDto.getRoleId())){
             role = roleService.get(employeeDto.getRoleId());

        }

    return new Employee(
            employeeDto.getUsername(),
            employeeDto.getName(),
            employeeDto.getEmail(),
            employeeDto.getDesignation(),
            employeeDto.getContactNumber(),
            passwordFormat,
            employeeDto.getLocationDetail(),
            employeeDto.getGender(),
            role
    );
}
}
