package net.edgefox.spring.errors;

import org.springframework.http.HttpStatus;

/**
 * Created by ilyutov on 05.08.15.
 */
public class EventNotFoundException extends ApplicationException {
    public EventNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("Event with id %d was not found", id));
    }
}
