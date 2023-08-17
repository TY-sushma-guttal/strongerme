package com.tyss.strongameapp.exception;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(1)
public class SQLExceptionHandler {


	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> SQLExceptionHandling(SQLException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), "Oops..!Operation Failed", request.getDescription(false),true);
		log.error(exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MyFileNotFoundException.class)
	public ResponseEntity<?> fileNotFoundExceptionHandling(MyFileNotFoundException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(),exception.getMessage(), request.getDescription(false), true);
		log.error(exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(SessionException.class)
	public ResponseEntity<?> SessoinExceptionHandling(SessionException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), "Oops..!Opearion Failed", request.getDescription(false),true);
		log.error(exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}



}
