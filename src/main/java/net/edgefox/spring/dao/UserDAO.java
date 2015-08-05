package net.edgefox.spring.dao;

/**
 * Created by edgefox on 8/2/15.
 */

import net.edgefox.spring.entities.User;

import java.util.Collection;

public interface UserDAO {
    User save(User user);
    void delete(User user);
    User getUserByID(long id);
    Collection<User> listUsers();
    Collection<User> listEventUsers(long eventId);
}
