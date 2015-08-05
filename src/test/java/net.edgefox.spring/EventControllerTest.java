package net.edgefox.spring;

import net.edgefox.spring.entities.User;
import net.edgefox.spring.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ilyutov on 05.08.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-application.xml")
@WebAppConfiguration
public class EventControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userServiceMock;
    @Autowired
    private WebApplicationContext wac;

    private String newUserjson = "{\"id\": 0, \"firstName\": \"Ivan\", \"lastName\": \"Lyutov\", \"email\": \"ivanlyutov@gmail.com\"}";
    private String createdUserjson = "{\"id\": 1, \"firstName\": \"Ivan\", \"lastName\": \"Lyutov\", \"email\": \"ivanlyutov@gmail.com\"}";
    private String existingUserjson = "{\"id\": 1, \"firstName\": \"Vasya\", \"lastName\": \"Pupkin\", \"email\": \"vasya.pupkin@gmail.com\"}";

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUser() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Lyutov");
        user.setEmail("ivanlyutov@gmail.com");

        when(userServiceMock.save(user)).thenReturn(user);
        final ResultActions test = mockMvc.perform(post("/users").content(newUserjson).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        test.andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Lyutov"))
                .andExpect(jsonPath("$.email").value("ivanlyutov@gmail.com"));
        verifyNoMoreInteractions(userServiceMock);
    }
}
