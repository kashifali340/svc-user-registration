package com.lumimindsinc.domain_name.usersregistration.user.registration.service;


import com.lumimindsinc.domain_name.usersregistration.user.registration.dto.EmployeeDto;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.InvalidUserDataException;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Service
public interface IUserService {
    Message create(Principal principal, EmployeeDto employeeDto, MultipartFile multipartFile);

    Message delete(Principal principal, Long userId);

    Message changeStatus(Principal principal, Long userId, boolean parseBoolean);

    Message get(Principal principal, String email);

    Message getbyUserId(Principal principal, Long userId);

    Message getAll(Principal principal, Boolean isActive, int page, int size);

    Message updateUser(Principal principal, Long userId, EmployeeDto employeeDto);

    Message updateResume(Principal principal, MultipartFile multipartFile);

    Employee getByEmail(String email) throws RecordNotFoundException, InvalidUserDataException;

}
