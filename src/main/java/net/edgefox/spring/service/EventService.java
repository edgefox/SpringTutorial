package net.edgefox.spring.service;

import net.edgefox.spring.dao.EventDAO;
import net.edgefox.spring.entities.Event;
import net.edgefox.spring.errors.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by ilyutov on 05.08.15.
 */
@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;

    public Event save(Event event) {
        Event updatedOrCreated = eventDAO.save(event);
        if (updatedOrCreated == null) {
            throw new EventNotFoundException(event.getId());
        }

        return updatedOrCreated;
    }

    public void delete(long id) {
        Event event = eventDAO.getEventByID(id);
        if (event == null) {
            throw new EventNotFoundException(id);
        }
        eventDAO.delete(event);
    }

    public Event getEventByID(long id) {
        Event event = eventDAO.getEventByID(id);
        if (event == null) {
            throw new EventNotFoundException(id);
        }

        return event;
    }

    public Collection<Event> listEvents() {
        return eventDAO.listEvents();
    }

    public Collection<Event> listUserEvents(long userId) {
        return eventDAO.listUserEvents(userId);
    }

    public Event inviteUser(Long eventId, Long userId) {
        return eventDAO.inviteUser(eventId, userId);
    }
}
