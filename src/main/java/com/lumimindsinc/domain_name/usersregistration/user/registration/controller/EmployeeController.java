package com.lumimindsinc.domain_name.usersregistration.user.registration.controller;


import com.amazonaws.services.s3.model.S3Object;
import com.lumimindsinc.domain_name.usersregistration.user.registration.dto.EmployeeDto;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.IUserService;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.CannotSaveRecordException;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.InvalidUserDataException;
import com.lumimindsinc.domain_name.usersregistration.general.exceptions.RecordNotFoundException;
import com.lumimindsinc.domain_name.usersregistration.user.registration.service.MetadataService;
import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/1.0/employee")
public class EmployeeController {

    @Autowired
    private IUserService userService;
    @Autowired
    private MetadataService metadataService;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity create(@Valid @NotNull Principal principal, @ModelAttribute(value = "employeeDto") EmployeeDto employeeDto,
                                 @RequestPart(value = "file",   required = false) MultipartFile multipartFile){

        Message message = userService.create(principal, employeeDto, multipartFile);
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @PatchMapping(value = "/update/resume", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity updateResume(@Valid @NotNull Principal principal, @RequestPart(value = "file",   required = false) MultipartFile multipartFile){

        Message message = userService.updateResume(principal, multipartFile);
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @PutMapping("/delete/{userId}")
    public ResponseEntity delete(@Valid @NotNull Principal principal, @PathVariable Long userId)  {
        Message message = userService.delete(principal, userId);
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @PutMapping("/change/status/{userId}/{status}")
    public ResponseEntity changeStatus(@Valid @NotNull Principal principal, @PathVariable Long userId, @PathVariable String status)  {
        Message message = userService.changeStatus(principal, userId, Boolean.parseBoolean(status));
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @GetMapping("/get/email")
    public ResponseEntity get(@Valid @NotNull Principal principal, @RequestParam String email) throws RecordNotFoundException, InvalidUserDataException {
        Message message = userService.get(principal, email);
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @GetMapping("/get/id")
    public ResponseEntity get(@Valid @NotNull Principal principal, @RequestParam Long userId) throws RecordNotFoundException, InvalidUserDataException {
        Message message = userService.getbyUserId(principal, userId);
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @GetMapping("/get/employeedetail")
    public ResponseEntity get(@Valid @NotNull Principal principal) throws RecordNotFoundException, InvalidUserDataException {
        Message message = userService.get(principal, principal.getName());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @GetMapping("/get/all/{isActive}")
    public ResponseEntity getAll(@Valid Principal principal, @PathVariable Boolean isActive,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size) throws InvalidUserDataException, RecordNotFoundException {
        Message message = userService.getAll(principal, isActive, page, size);
        return ResponseEntity.status(message.getCode()).body(message);
    }

      @PatchMapping("/update/{employeeId}")
    public ResponseEntity updateEmployee(@Valid @NotNull Principal principal, @PathVariable Long employeeId, @RequestBody EmployeeDto employeeDto) throws RecordNotFoundException, CannotSaveRecordException, InvalidUserDataException {
        Message message = userService.updateUser(principal, employeeId, employeeDto);
        return ResponseEntity.status(message.getCode()).body(message);
    }
    @GetMapping("download/{id}")
    @ResponseBody
    public HttpEntity<byte[]> download(Model model, @PathVariable int id, HttpServletResponse response) throws
            IOException {

        S3Object s3Object = metadataService.download(id);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<byte[]>(bytes, header);
    }

    @GetMapping("get/all/cvs")
    public ResponseEntity getAllUserCvs(@Valid @NotNull Principal principal, @RequestParam Long userId){
        Message message = metadataService.getAllUserCvs(principal, userId);
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @GetMapping("get/cv")
    public ResponseEntity getUserCvById(@Valid @NotNull Principal principal, @RequestParam Long fileMetaId){
        Message message = metadataService.getUserCvById(principal, fileMetaId);
        return ResponseEntity.status(message.getCode()).body(message);
    }
    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        ByteArrayOutputStream downloadInputStream = metadataService.downloadFile(filename);

        return ResponseEntity.ok()
                .contentType(contentType(filename))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(downloadInputStream.toByteArray());
    }


    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}

