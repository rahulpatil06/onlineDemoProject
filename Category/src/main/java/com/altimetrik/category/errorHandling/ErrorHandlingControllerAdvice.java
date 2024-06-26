package com.altimetrik.category.errorHandling;

import brave.Tracer;
import com.altimetrik.category.dto.CategoryResponse;
import com.altimetrik.category.dto.Header;
import jakarta.el.MethodNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.nio.file.AccessDeniedException;
import java.util.*;

@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice extends Throwable {

    @Autowired
    Tracer tracer;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<CategoryResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("Inside handleValidationExceptions() method in ErrorHandlingControllerAdvice ....");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        CustomErrorResponse error = new CustomErrorResponse(new Date(),ErroCodes.getCode(ex.getStatusCode()),ex.getStatusCode(), errors);
        log.error("CustomErrorResponse in ErrorHandlingControllerAdvice .... "+error.getMessage()+"TraceId "+tracer.currentSpan().context().traceIdString());
        Header header = new Header();
        header.setCode(String.valueOf(ErroCodes.getCode(HttpStatus.BAD_REQUEST)));
        header.setMessage("Error....!!!");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setHeader(header);
        categoryResponse.setErrorDetails(error);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).header("traceId",tracer.currentSpan().context().traceIdString()).body(categoryResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public  ResponseEntity<CategoryResponse> handleAllExceptions(Exception ex)
    {
        log.error("Inside handleAllExceptions() method in ErrorHandlingControllerAdvice ....");
        Map<String, String> errors = new HashMap<>();
            errors.put("errorMessage", ex.getMessage());
        CustomErrorResponse error = new CustomErrorResponse(new Date(),ErroCodes.getCode(HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR,errors);
        log.error("CustomErrorResponse in ErrorHandlingControllerAdvice .... "+error.getMessage()+"TraceId "+tracer.currentSpan().context().traceIdString());
        Header header = new Header();
        header.setCode(String.valueOf(ErroCodes.getCode(HttpStatus.INTERNAL_SERVER_ERROR)));
        header.setMessage("Error....!!!");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setHeader(header);
        categoryResponse.setErrorDetails(error);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("traceId",tracer.currentSpan().context().traceIdString()).body(categoryResponse);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public  ResponseEntity<CategoryResponse>  handleNullPointerException(NullPointerException ex){
        log.error("Inside handleNullPointerException() method in ErrorHandlingControllerAdvice ....");
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        CustomErrorResponse error = new CustomErrorResponse(new Date(),ErroCodes.getCode(HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND,errors);
        log.error("CustomErrorResponse in ErrorHandlingControllerAdvice .... "+error.getMessage()+"TraceId "+tracer.currentSpan().context().traceIdString());
        Header header = new Header();
        header.setCode(String.valueOf(ErroCodes.getCode(HttpStatus.NOT_FOUND)));
        header.setMessage("Error....!!!");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setHeader(header);
        categoryResponse.setErrorDetails(error);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).header("traceId",tracer.currentSpan().context().traceIdString()).body(categoryResponse);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodNotFoundException.class)
    @ResponseBody
    public  ResponseEntity<CategoryResponse>  handleMethodNotFound(MethodNotFoundException ex){
        log.error("Inside handleMethodNotFound() method in ErrorHandlingControllerAdvice ....");
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage","Method Not Found");
        CustomErrorResponse error = new CustomErrorResponse(new Date(),ErroCodes.getCode(HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND,errors);
        log.error("CustomErrorResponse in ErrorHandlingControllerAdvice .... "+error.getMessage()+"TraceId "+tracer.currentSpan().context().traceIdString());
        Header header = new Header();
        header.setCode(String.valueOf(ErroCodes.getCode(HttpStatus.NOT_FOUND)));
        header.setMessage("Error....!!!");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setHeader(header);
        categoryResponse.setErrorDetails(error);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).header("traceId",tracer.currentSpan().context().traceIdString()).body(categoryResponse);
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public  ResponseEntity<CategoryResponse>  handleMethodNotFound(AccessDeniedException ex){
        log.error("Inside handleMethodNotFound() method in ErrorHandlingControllerAdvice ....");
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage","User Not Authorized to Access ...!!!!");
        CustomErrorResponse error = new CustomErrorResponse(new Date(),ErroCodes.getCode(HttpStatus.FORBIDDEN),HttpStatus.FORBIDDEN,errors);
        log.error("CustomErrorResponse in ErrorHandlingControllerAdvice .... "+error.getMessage()+"TraceId "+tracer.currentSpan().context().traceIdString());
        Header header = new Header();
        header.setCode(String.valueOf(ErroCodes.getCode(HttpStatus.FORBIDDEN)));
        header.setMessage("Error....!!!");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setHeader(header);
        categoryResponse.setErrorDetails(error);
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).header("traceId",tracer.currentSpan().context().traceIdString()).body(categoryResponse);
    }
}
