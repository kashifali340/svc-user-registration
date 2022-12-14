package com.lumimindsinc.domain_name.usersregistration.general.exceptions;

import com.lumimindsinc.domain_name.usersregistration.utill.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex){
        Message message = new Message("Failure", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(SamePreviousCurrentPasswordException.class)
    protected ResponseEntity<Object> handlePasswordMatch(SamePreviousCurrentPasswordException ex){
//        Message message = new Message("Failure", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        Message message = new Message(
                HttpStatus.BAD_REQUEST.toString(),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );

        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }
    @ExceptionHandler(EmailNotFoundException.class)
    protected ResponseEntity<Object> emailNotFoundException(EmailNotFoundException ex){
        Message message = new Message("Failure", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(UnAuthorizedUserException.class)
    protected ResponseEntity<Object> handleUnAuthorizedUser(UnAuthorizedUserException ex){
        Message message = new Message("UNAUTHORIZED User", ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(CannotSaveRecordException.class)
    protected ResponseEntity<Object> handleCannotSaveRecord(CannotSaveRecordException ex){
        Message message = new Message("Failure", ex.getMessage(), 422);
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        Message message = new Message("Failure", ex.getMessage(), 404);
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(InvalidUserDataException.class)
    protected ResponseEntity<Object> handleInvalidUserData(InvalidUserDataException ex){
        Message message = new Message("Failure", ex.getMessage(), 404);
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleInvalidToken(InvalidTokenException ex){
        Message message = new Message("Failure", ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @ExceptionHandler(BulkInvalidateDataException.class)
    protected ResponseEntity<Object> handleBulkInvalidateData(BulkInvalidateDataException ex){
        Message message = new Message("Failure", ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        log.error(ex.getMessage());
        return ResponseEntity.status(message.getCode()).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> map = new HashMap<>();

        ex.getBindingResult().getAllErrors().stream().forEach(e->{
            Object[] obj = e.getArguments();
            map.put(((DefaultMessageSourceResolvable) obj[0]).getCode(), e.getDefaultMessage());
        });

        Message m = new Message();
        m.setMessage("Validation Failed").setStatus("Failure").setCode(400).setData(map);
        return ResponseEntity.status(400)
                .body(m);
    }
}
