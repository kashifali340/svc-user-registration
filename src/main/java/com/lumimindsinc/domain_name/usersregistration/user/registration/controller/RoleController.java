package com.lumimindsinc.domain_name.usersregistration.user.registration.controller;


import com.lumimindsinc.domain_name.usersregistration.user.registration.service.RoleService;
import com.lumimindsinc.domain_name.usersregistration.utill.Constants;
import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/1.0/role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/get/id")
    public ResponseEntity get(Principal principal, @RequestParam Long id) throws RecordNotFoundException {
        Message message = new Message(Constants.SERVICE_SUCCESS_STATUS, "Role retrieved successfully with Id: " +id, Constants.SERVICE_SUCCESS_CODE, roleService.get(id));
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @GetMapping("/get/all")
    public ResponseEntity get(Principal principal) throws RecordNotFoundException {
        Message message = new Message(Constants.SERVICE_SUCCESS_STATUS, "All roles retrieved successfully", Constants.SERVICE_SUCCESS_CODE, roleService.getAll(principal));
        return ResponseEntity.status(message.getCode()).body(message);
    }

}

