package net.edgefox.spring;

import net.edgefox.spring.entities.Event;
import net.edgefox.spring.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ilyutov on 05.08.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-application.xml")
@WebAppConfiguration
public class EventControllerTest {
    private static final String newEventJson = "{\"id\": 0, \"title\": \"Test Event\"}";
    @Autowired
    private EventService eventServiceMock;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event();
        event.setTitle("Test Event");
        when(eventServiceMock.save(event)).thenReturn(event);

        mockMvc.perform(post("/events").content(newEventJson).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(event.getTitle()));
    }

    @Test
    public void testGetEvent() throws Exception {
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        when(eventServiceMock.getEventByID(event.getId())).thenReturn(event);

        mockMvc.perform(get("/events/{id}", event.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value((int) event.getId()))
                .andExpect(jsonPath("$.title").value(event.getTitle()));
    }

    @Test
    public void testRemoveEvent() throws Exception {
        Event event = new Event();
        event.setId(1);
        event.setTitle("Test Event");

        mockMvc.perform(delete("/events/{id}", event.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(event.getId())));

        verify(eventServiceMock, times(1)).delete(event.getId());
    }
}
