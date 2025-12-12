package com.learn.spring.tickets.controllers;

import com.learn.spring.tickets.domain.DTOs.ErrorDTO;
import com.learn.spring.tickets.exceptions.EventNotFoundException;
import com.learn.spring.tickets.exceptions.EventUpdateException;
import com.learn.spring.tickets.exceptions.TicketTypeNotFoundException;
import com.learn.spring.tickets.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalResponseHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException ex){
        log.error("Caught ConstraintViolationException ",ex);
        ErrorDTO errorDTO = new ErrorDTO();

        String errMessage = ex.getConstraintViolations().stream().findFirst()
                .map(violation -> violation.getPropertyPath() + " : " + violation.getMessage())
                .orElse("Constraint Violation occurred");


        errorDTO.setError(errMessage);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("Caught MethodArgumentNotValidException ",ex);
        ErrorDTO errorDTO = new ErrorDTO();

        String validationErrorMessage = ex.getBindingResult().getFieldErrors()
                .stream().findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation error occurred");

        errorDTO.setError(validationErrorMessage);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException ex){
        log.error("Caught UserNotFoundException  ",ex);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("User not found");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEventNotFoundException(EventNotFoundException ex){
        log.error("Caught EventNotFoundException  ",ex);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Event not found");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex){
        log.error("Caught TicketTypeNotFoundException  ",ex);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Ticket Type not found");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorDTO> EventUpdateException(EventUpdateException ex){
        log.error("Caught EventUpdateException  ",ex);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Unable to update event");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex){
        log.error("Caught exception ",ex);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("An unknown error occurred!!!");
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


}
