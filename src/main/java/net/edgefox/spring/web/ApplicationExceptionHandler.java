package net.edgefox.spring.web;

import net.edgefox.spring.entities.ApplicationError;
import net.edgefox.spring.errors.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public @ResponseBody ApplicationError handleApplicationException(ApplicationException ex,
                                                                     HttpServletResponse response) {
        response.setStatus(ex.getStatus().value());
        return new ApplicationError(ex.getStatus().value(), ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody ApplicationError handleValidationException(ConstraintViolationException ex,
                                                                    HttpServletResponse response) {
        List<String> errors = new ArrayList<String>(ex.getConstraintViolations().size());
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(String.format("Validation error: %s. Provided value: %s",
                    violation.getMessage(), violation.getInvalidValue()));
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                StringUtils.collectionToDelimitedString(errors, ","));
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody ApplicationError handleOtherExceptions(Exception ex,
                                                                HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
