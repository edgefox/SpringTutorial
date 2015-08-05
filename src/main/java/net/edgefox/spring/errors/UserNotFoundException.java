package net.edgefox.spring.errors;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("User with id %d was not found", id));
    }
}
