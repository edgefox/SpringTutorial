package net.edgefox.spring.dao;

import net.edgefox.spring.entities.Event;
import net.edgefox.spring.entities.User;
import net.edgefox.spring.errors.ApplicationException;
import net.edgefox.spring.errors.EventNotFoundException;
import net.edgefox.spring.errors.UserNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by ilyutov on 05.08.15.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class DBEventDAO implements EventDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Event save(Event user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user;
    }

    @Override
    public void delete(Event user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public Event getEventByID(long id) {
        return (Event) sessionFactory.getCurrentSession().get(Event.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Event> listEvents() {
        return sessionFactory.getCurrentSession().createQuery("from Event").setMaxResults(100).list();
    }

    public Collection<Event> listUserEvents(long userId) {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        Hibernate.initialize(user.getEvents());
        return user.getEvents();
    }

    @Override
    public Event inviteUser(Long eventId, Long userId) {
        final Session session = sessionFactory.getCurrentSession();
        Event event = (Event) session.get(Event.class, eventId);
        if (event == null) {
            throw new EventNotFoundException(eventId);
        }
        User user = (User) session.get(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        Hibernate.initialize(event.getUsers());
        event.getUsers().add(user);

        return event;
    }
}
