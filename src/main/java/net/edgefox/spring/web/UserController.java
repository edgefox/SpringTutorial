package net.edgefox.spring.web;

import net.edgefox.spring.entities.User;
import net.edgefox.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @Valid
    public @ResponseBody User save(@RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody User getUserByID(@PathVariable("id") Long id) {
        return userService.getUserByID(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public @ResponseBody Long delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return id;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection<User> listUsers() {
        return userService.listUsers();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/event/{eventId}")
    public @ResponseBody Collection<User> listEventUsers(@PathVariable("eventId") Long eventId) {
        return userService.listEventUsers(eventId);
    }
}
