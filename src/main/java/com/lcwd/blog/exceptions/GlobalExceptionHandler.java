package com.lcwd.blog.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.lcwd.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
		
		Map<String,String> errMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((e)->{
			
			errMap.put(((FieldError) e).getField(),((FieldError) e).getDefaultMessage());
			
		});
		
		return new ResponseEntity<Map<String,String>>(errMap, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(HandlerMethodValidationException.class) 
	public ResponseEntity<ApiResponse> handlerMethodValidationExceptionHandler(HandlerMethodValidationException ex){
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getReason(), false), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class) 
	public ResponseEntity<ApiResponse>  sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex){
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
	}
}
