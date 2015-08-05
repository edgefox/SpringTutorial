package net.edgefox.spring.errors;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {
    private HttpStatus status;

    public ApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApplicationException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
