package net.edgefox.spring.web;

import net.edgefox.spring.entities.Event;
import net.edgefox.spring.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Created by ilyutov on 05.08.15.
 */
@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    @Valid
    public @ResponseBody Event save(@RequestBody Event event) {
        return eventService.save(event);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody Event getEventByID(@PathVariable("id") Long id) {
        return eventService.getEventByID(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public @ResponseBody Long delete(@PathVariable("id") Long id) {
        eventService.delete(id);
        return id;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection<Event> listEvents() {
        return eventService.listEvents();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public @ResponseBody Collection<Event> listUserEvents(@PathVariable("userId") Long userId) {
        return eventService.listUserEvents(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{eventId}/invite/{userId}")
    public @ResponseBody Event inviteUser(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        return eventService.inviteUser(eventId, userId);
    }
}
