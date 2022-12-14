package com.lumimindsinc.domain_name.usersregistration.user.registration.repositories;


import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.FileMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMetaRepository extends CrudRepository<FileMeta, Integer> {

    FileMeta getById(Long fileMetaId);

    List<FileMeta> findAllByEmployee(Employee employee);
}