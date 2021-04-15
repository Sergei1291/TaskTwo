package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({ControllerException.class})
    public ResponseEntity<Object> handleException(ControllerException controllerException,
                                                  Locale locale) {
        String exceptionMessage = controllerException.getMessage();
        String messageBundle = messageSource.getMessage(exceptionMessage, null, locale);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("errorMessage", messageBundle);
        parameters.put("errorCode", controllerException.getErrorCode());
        return new ResponseEntity<>(parameters, controllerException.getStatus());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleUnregisterException(Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.50001", null, locale);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("errorMessage", messageBundle);
        parameters.put("errorCode", 50001);
        return new ResponseEntity<>(parameters, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}