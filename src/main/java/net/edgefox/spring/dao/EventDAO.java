package net.edgefox.spring.dao;

import net.edgefox.spring.entities.Event;

import java.util.Collection;

/**
 * Created by ilyutov on 05.08.15.
 */
public interface EventDAO {
    Event save(Event user);
    void delete(Event user);
    Event getEventByID(long id);
    Collection<Event> listEvents();
    Collection<Event> listUserEvents(long userId);
    Event inviteUser(Long eventId, Long userId);
}
