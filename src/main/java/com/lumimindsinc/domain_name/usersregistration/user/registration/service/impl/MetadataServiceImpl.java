package com.lumimindsinc.domain_name.usersregistration.user.registration.service.impl;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.lumimindsinc.domain_name.usersregistration.user.registration.repositories.EmployeeRepository;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.AmazonS3Service;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.FileMeta;
import com.lumimindsinc.domain_name.usersregistration.user.registration.repositories.FileMetaRepository;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.MetadataService;
import com.lumimindsinc.domain_name.usersregistration.utill.Constants;
import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private FileMetaRepository fileMetaRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void upload(MultipartFile file, Employee employee) throws IOException {

        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, "uploadCVs");
        String fileName = String.format("%s", file.getOriginalFilename());
        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path, fileName, Optional.of(metadata), file.getInputStream());
        // Saving metadata to db
        fileMetaRepository.save(new FileMeta(fileName, path, putObjectResult.getMetadata().getVersionId(), employee));
    }

    @Override
    public S3Object download(int id) {
        FileMeta fileMeta = fileMetaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return amazonS3Service.download(fileMeta.getFilePath(),fileMeta.getFileName());
    }

    @Override
    public List<FileMeta> list() {
        List<FileMeta> metas = new ArrayList<>();
        fileMetaRepository.findAll().forEach(metas::add);
        return metas;
    }

    @Override
    public Message getAllUserCvs(Principal principal, Long userId) {
        List<FileMeta> metas = new ArrayList<>();
        Employee employee = employeeRepository.getById(userId);
        metas = fileMetaRepository.findAllByEmployee(employee);
        if (!StringUtils.isEmpty(metas)){
            return new Message<>(Constants.SERVICE_SUCCESS_STATUS, "Meta Data retrieved successfully", Constants.SERVICE_SUCCESS_CODE, metas);
        }else
            return new Message<>(Constants.RECORD_NOT_FOUND_STATUS, "Meta Data retrieved successfully", Constants.RECORD_NOT_FOUND_CODE);
    }

    @Override
    public Message getUserCvById(Principal principal, Long fileMetaId) {

        FileMeta meta = fileMetaRepository.getById(fileMetaId);
        if (!Objects.isNull(meta)){
            return new Message<>(Constants.SERVICE_SUCCESS_STATUS, "Meta Data retrieved successfully", Constants.SERVICE_SUCCESS_CODE, meta);
         }else
            return new Message<>(Constants.RECORD_NOT_FOUND_STATUS, "Meta Data retrieved successfully", Constants.RECORD_NOT_FOUND_CODE);
    }
    public ByteArrayOutputStream downloadFile(String keyName) {
        try {
            S3Object s3object = amazonS3.getObject(new GetObjectRequest(bucketName, keyName));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            return outputStream;
        } catch (IOException ioException) {
            log.error("IOException: " + ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
            log.info("AmazonServiceException Message:    " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            log.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }

        return null;
    }

}