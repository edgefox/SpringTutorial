package net.edgefox.spring.service;

import net.edgefox.spring.dao.UserDAO;
import net.edgefox.spring.entities.User;
import net.edgefox.spring.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User save(User user) {
        User updatedOrCreated = userDAO.save(user);
        if (updatedOrCreated == null) {
            throw new UserNotFoundException(user.getId());
        }
        return updatedOrCreated;
    }

    public void delete(long id) {
        User user = userDAO.getUserByID(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
    }

    public User getUserByID(long id) {
        User user = userDAO.getUserByID(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return user;
    }

    public Collection<User> listUsers() {
        return userDAO.listUsers();
    }

    public Collection<User> listEventUsers(long eventId) {
        return userDAO.listEventUsers(eventId);
    }
}
