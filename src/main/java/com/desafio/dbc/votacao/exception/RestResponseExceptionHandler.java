package com.desafio.dbc.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.desafio.dbc.votacao.dto.ErroDto;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		ErroDto erro = ErroDto.builder().httpStatus(HttpStatus.NOT_FOUND.value()).mensagem("Resultado não encontrado").build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler({ Exception.class, RuntimeException.class })
	public ResponseEntity<Object> handleInternalErrorException(Exception ex, WebRequest request) {
		ErroDto erro = ErroDto.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).mensagem(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
	
	@ExceptionHandler({ VotoException.class })
	public ResponseEntity<Object> handleVotoErrorException(Exception ex, WebRequest request) {
		ErroDto erro = ErroDto.builder().httpStatus(HttpStatus.BAD_REQUEST.value()).mensagem(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	
}
