package com.spring.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.osworks.api.exceptionhandler.ErrorResponse.Campo;
import com.spring.osworks.domain.exception.DomainException;
import com.spring.osworks.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		
		var errorResponse = new ErrorResponse();
		errorResponse.setStatus(status.value());
		errorResponse.setTitulo(ex.getMessage());
		errorResponse.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		
		var errorResponse = new ErrorResponse();
		errorResponse.setStatus(status.value());
		errorResponse.setTitulo(ex.getMessage());
		errorResponse.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = new ArrayList<Campo>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new ErrorResponse.Campo(nome, mensagem));
		}
		
		var errorResponse = new ErrorResponse();
		errorResponse.setStatus(status.value());
		errorResponse.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
		errorResponse.setDataHora(OffsetDateTime.now());
		errorResponse.setCampos(campos);
		
		return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}
}
