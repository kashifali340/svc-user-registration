package com.lumimindsinc.domain_name.usersregistration.user.registration.service.impl;



import com.lumimindsinc.domain_name.usersregistration.user.registration.dto.EmployeeDto;
import com.lumimindsinc.domain_name.usersregistration.user.registration.mapper.EmployeeMapper;
import com.lumimindsinc.domain_name.usersregistration.user.registration.repositories.EmployeeRepository;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.IUserService;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.MetadataService;
import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.InvalidUserDataException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Roles;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import com.lumimindsinc.domain_name.usersregistration.utill.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;


@Slf4j
@Service
public class EmployeeServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MetadataService metadataService;

    @Override
    public Message create(Principal principal, EmployeeDto employeeDto, MultipartFile multipartFile) {
        try {
            Employee employee = saveEmployee(
                    employeeMapper.mapDtoToModel(
                            employeeDto)
                    );
            if (!Objects.isNull(multipartFile)) {
               // fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                String checkforupload = "";
                try {
                    metadataService.upload(multipartFile, employee);
                    //    S3Util.uploadFile(fileName, multipartFile.getInputStream());
                    checkforupload = "Your file has been uploaded successfully!";
                } catch (Exception ex) {
                    checkforupload = "Error uploading file: " + ex.getMessage();
                }
            }
          log.info("User saved successfully, employee name : " + employee.getName());
            return new Message(Constants.SERVICE_SUCCESS_STATUS, "User saved successfully", Constants.SERVICE_SUCCESS_CODE);
        } catch (Exception e){
            e.printStackTrace();
            return new Message(Constants.INTERNAL_SERVER_STATUS, "Internal Server Error!", Constants.INTERNAL_SERVER_CODE);
        }
    }

    private Employee saveEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Message delete(Principal principal, Long employeeId) {
        if (employeeRepository.existsById(employeeId)){
            employeeRepository.deleteById(employeeId);
            return new Message(Constants.SERVICE_SUCCESS_STATUS, "Delete successfully", Constants.SERVICE_SUCCESS_CODE);
        }else
            return new Message(Constants.RECORD_NOT_FOUND_STATUS, "employee not found", Constants.RECORD_NOT_FOUND_CODE);
    }

    @Override
    public Message changeStatus(Principal principal, Long employeeId, boolean status) {
        Employee employee = employeeRepository.getById(employeeId);
        if (status) {
            employee.setDateDisabled(new Timestamp(System.currentTimeMillis()));
         //   employee.setDisabledBy(getByEmail(principal.getName()));
        } else {
            employee.setDateDisabled(new Timestamp(System.currentTimeMillis()));
         //  employee.setDisabledBy(getByEmail(principal.getName()));
        }
        employee.setIsActive(status);
        employee.setDateUpdated(new Timestamp(System.currentTimeMillis()));
      //  employee.setUpdatedBy(getByEmail(principal.getName()));
        saveEmployeeAndFlush(employee);
        log.info("Employee status changed successfully, Employee id: " + employeeId);
        return new Message(Constants.SERVICE_SUCCESS_STATUS, "User status changed successfully", Constants.SERVICE_SUCCESS_CODE);
    }

    Employee saveEmployeeAndFlush(Employee employee)  {
        Employee saveUsers = employeeRepository.saveAndFlush(employee);
            return saveUsers;
    }

    @Override
    public Message get(Principal principal, String email) {
        if (employeeRepository.existsByEmail(email)){
            Employee employee = employeeRepository.getByEmail(email);
            return new Message(Constants.SERVICE_SUCCESS_STATUS, "employee retrieved successfully", Constants.SERVICE_SUCCESS_CODE, employee);
        }else
            return new Message(Constants.RECORD_NOT_FOUND_STATUS, "employee not found", Constants.RECORD_NOT_FOUND_CODE);
    }

    @Override
    public Message getbyUserId(Principal principal, Long employeeId) {
        if (employeeRepository.existsById(employeeId)){
            Employee employee = employeeRepository.findById(employeeId).get();
            return new Message(Constants.SERVICE_SUCCESS_STATUS, "employee retrieved successfully", Constants.SERVICE_SUCCESS_CODE, employee);
        }else
            return new Message(Constants.RECORD_NOT_FOUND_STATUS, "employee not found", Constants.RECORD_NOT_FOUND_CODE);

    }

    @Override
    public Message getAll(Principal principal, Boolean isActive, int page, int size) {

        List<Employee> employeeList = new ArrayList<>();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        Pageable paging = PageRequest.of(page-1, size);
        Map<String, Object> response = new HashMap<>();
        Page<Employee> employeePage = new PageImpl<>(employeeList);
        try {
            employeePage = employeeRepository.findAllByIsActiveOrderByIdDesc(isActive, paging);
            employeeList = employeePage.getContent();
            if (employeeList.size() > 0) {
                response.put("employeeList", employeeList);
                response.put("currentPage", employeePage.getNumber());
                response.put("totalItems", employeePage.getTotalElements());
                response.put("totalPages", employeePage.getTotalPages());
                return new Message(Constants.SERVICE_SUCCESS_STATUS, "Users retrieved successfully", Constants.SERVICE_SUCCESS_CODE, response);
            }
            else
                throw new RecordNotFoundException("No user found");

        }catch (RecordNotFoundException e) {
            e.printStackTrace();
            return new Message(Constants.RECORD_NOT_FOUND_STATUS, "Users Not Found ", Constants.RECORD_NOT_FOUND_CODE);
        }
        catch (Exception e ){
            e.printStackTrace();
            return new Message(Constants.INTERNAL_SERVER_STATUS, "Internal Server Error!" , Constants.INTERNAL_SERVER_CODE);
        }
    }

    @Override
    public Message updateUser(Principal principal, Long employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId).get();
        if (!Objects.isNull(employee)){
            employee.setEmail(employeeDto.getEmail());
            employee.setDesignation(employeeDto.getDesignation());
            employee.setGender(employeeDto.getGender());
            employee.setContactNumber(employeeDto.getContactNumber());
            employee.setUsername(employeeDto.getUsername());
            employee.setName(employeeDto.getName());
            employee.setLocationDetail(employeeDto.getLocationDetail());
            employee.setIsActive(Boolean.TRUE);
            employee.setDateUpdated(new Timestamp(new Date().getTime()));

            employee = saveEmployeeAndFlush(employee);
            return new Message(Constants.SERVICE_SUCCESS_STATUS, "employee updated successfully", Constants.SERVICE_SUCCESS_CODE, employeeId);
        }else
            return new Message(Constants.SERVICE_UNSUCCESS_STATUS, "employee not updated successfully", Constants.SERVICE_UNSUCCESS_CODE, employeeId);
    }

    @Override
    public Message updateResume(Principal principal, MultipartFile multipartFile) {
        try {
            Employee employee = employeeRepository.getByEmail(principal.getName());
            if (!Objects.isNull(multipartFile)) {
                String checkforupload = "";
                try {
                    metadataService.upload(multipartFile, employee);
                    checkforupload = "Your file has been uploaded successfully!";
                } catch (Exception ex) {
                    checkforupload = "Error uploading file: " + ex.getMessage();
                }
            }
            log.info("your resume upload successfully, employee name : " + employee.getName());
            return new Message(Constants.SERVICE_SUCCESS_STATUS, "your resume upload successfully", Constants.SERVICE_SUCCESS_CODE);
        } catch (Exception e){
            e.printStackTrace();
            return new Message(Constants.INTERNAL_SERVER_STATUS, "Internal Server Error!", Constants.INTERNAL_SERVER_CODE);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee users = employeeRepository.findByEmailIgnoreCase(email);
        if (users == null) {
            throw new NoSuchElementException("Email or password are incorrect! ");
        }
        return new org.springframework.security.core.userdetails.User(
                users.getEmail(), users.getPassword(), users.getIsActive(), true, true,
                true, getAuthorities(users.getRole()));

    }
    public Collection<GrantedAuthority> getAuthorities(Roles roles) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            public String getAuthority() {
                return roles.getType();
            }
        };
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

    @Override
    public Employee getByEmail(String email) throws RecordNotFoundException, InvalidUserDataException {
        if (email == null)
            throw new InvalidUserDataException("Email not provided");
        Employee employee = employeeRepository.findByEmailIgnoreCase(email);
        if (employee == null) {
            throw new RecordNotFoundException("User not found with this Email: " + email);
        } else {
            return employee;
        }
    }
}
