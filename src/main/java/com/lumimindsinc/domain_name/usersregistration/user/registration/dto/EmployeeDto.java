package com.lumimindsinc.domain_name.usersregistration.user.registration.dto;

import lombok.*;

import javax.persistence.Column;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    private String email;
    private String name;
    private String locationDetail;
    private String organization ;
    private String username;
    private String designation;
    private String contactNumber;
    private Boolean isActive;
    private String gender;
    private Long roleId;


}
