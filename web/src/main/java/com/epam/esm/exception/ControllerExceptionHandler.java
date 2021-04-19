package com.epam.esm.exception;

import com.epam.esm.exception.certificate.*;
import com.epam.esm.exception.tag.TagNameAlreadyExistsException;
import com.epam.esm.exception.tag.TagNameNotFoundException;
import com.epam.esm.exception.tag.TagNameNotValidException;
import com.epam.esm.exception.tag.TagNotFoundException;
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

    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_CODE = "errorCode";

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private ResponseEntity<Object> getResponseEntity(String errorMessage,
                                                     int errorCode,
                                                     HttpStatus httpStatus) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ERROR_MESSAGE, errorMessage);
        parameters.put(ERROR_CODE, errorCode);
        return new ResponseEntity<>(parameters, httpStatus);
    }

    @ExceptionHandler({TagNameAlreadyExistsException.class})
    public ResponseEntity<Object> handleException(TagNameAlreadyExistsException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40001", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40001, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TagNameNotFoundException.class})
    public ResponseEntity<Object> handleException(TagNameNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40002", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40002, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TagNameNotValidException.class})
    public ResponseEntity<Object> handleException(TagNameNotValidException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40003", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40003, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TagNotFoundException.class})
    public ResponseEntity<Object> handleException(TagNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40401", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40401, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CertificateDtoNotValidException.class})
    public ResponseEntity<Object> handleException(CertificateDtoNotValidException exception,
                                                  Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.40004", null, locale);
        return getResponseEntity(messageBundle, 40004, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CertificateNameAlreadyExistsException.class})
    public ResponseEntity<Object> handleException(CertificateNameAlreadyExistsException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40005", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40005, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CertificateNotFoundException.class})
    public ResponseEntity<Object> handleException(CertificateNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40402", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40402, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnsupportedSearchParamNameCertificateException.class})
    public ResponseEntity<Object> handleException(UnsupportedSearchParamNameCertificateException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40006", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40006, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnsupportedSortedParamNameCertificateException.class})
    public ResponseEntity<Object> handleException(UnsupportedSortedParamNameCertificateException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40007", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40007, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleUnregisterException(Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.50001", null, locale);
        return getResponseEntity(messageBundle, 50001, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}