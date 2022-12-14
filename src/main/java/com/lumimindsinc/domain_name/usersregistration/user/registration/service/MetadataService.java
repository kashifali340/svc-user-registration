package com.lumimindsinc.domain_name.usersregistration.user.registration.service;

import com.amazonaws.services.s3.model.S3Object;
import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.FileMeta;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface MetadataService {
    public void upload(MultipartFile file, Employee employee) throws IOException;
    public S3Object download(int id);
    public List<FileMeta> list();

    Message getAllUserCvs(Principal principal, Long userId);

    Message getUserCvById(Principal principal, Long fileMetaId);

    ByteArrayOutputStream downloadFile(String filename);
}