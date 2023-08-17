package com.tyss.adminstrongame.exception;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(1)
public class SQLExceptionHandler {
	
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> sqlExceptionHandling(SQLException exception, WebRequest request){
		String message= "Oops! opeartion failed";
		ErrorDetails errorDetails = 
				new ErrorDetails(true,message,new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
