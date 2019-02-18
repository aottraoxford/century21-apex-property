package com.century21.exception;

import com.century21.model.response.CustomResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    //@RequestParam require
    @Override
    protected ResponseEntity handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(400);
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }

    //@valid exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(400);
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(400);
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomResponse customResponse = new CustomResponse(400);
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }

    //@validated exception
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException ex) {
        CustomResponse customResponse = new CustomResponse(400);
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity customRuntimeException(CustomRuntimeException ex){
        CustomResponse customResponse=new CustomResponse(ex.getStatusCode());
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }

}
