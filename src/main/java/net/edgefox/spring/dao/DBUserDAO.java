package net.edgefox.spring.dao;

import net.edgefox.spring.entities.Event;
import net.edgefox.spring.entities.User;
import net.edgefox.spring.errors.EventNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class DBUserDAO implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user;
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public User getUserByID(long id) {
        return (User) sessionFactory.getCurrentSession().byId(User.class).load(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User").setMaxResults(100).list();
    }

    @Override
    public Collection<User> listEventUsers(long eventId) {
        Event event = (Event) sessionFactory.getCurrentSession().get(Event.class, eventId);
        if (event == null) {
            throw new EventNotFoundException(eventId);
        }

        Hibernate.initialize(event.getUsers());

        return event.getUsers();
    }


}
