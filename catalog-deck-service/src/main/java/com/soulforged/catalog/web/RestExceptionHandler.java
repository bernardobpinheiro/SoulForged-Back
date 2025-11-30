package com.soulforged.catalog.web;

import com.soulforged.common.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
}

@ExceptionHandler(IllegalStateException.class)
public ResponseEntity<?> handleConflict(IllegalStateException ex) {
return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
}

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
}

@ExceptionHandler(Exception.class)
public ResponseEntity<?> handleAny(Exception ex) {
ex.printStackTrace();
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "internal"));
}
}
