package net.edgefox.spring;

import net.edgefox.spring.entities.User;
import net.edgefox.spring.service.UserService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by edgefox on 8/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-application.xml")
@WebAppConfiguration
public class UserControllerTest {
    private static final String newUserJson = "{\"id\": 0, \"firstName\": \"Ivan\", \"lastName\": \"Lyutov\", \"email\": \"ivanlyutov@gmail.com\"}";
    @Autowired
    private UserService userServiceMock;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Lyutov");
        user.setEmail("ivanlyutov@gmail.com");

        when(userServiceMock.save(user)).thenReturn(user);
        mockMvc.perform(post("/users").content(newUserJson).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Lyutov"))
                .andExpect(jsonPath("$.email").value("ivanlyutov@gmail.com"));
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("Ivan");
        user.setLastName("Lyutov");
        user.setEmail("ivanlyutov@gmail.com");
        when(userServiceMock.getUserByID(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value((int)user.getId()))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Lyutov"))
                .andExpect(jsonPath("$.email").value("ivanlyutov@gmail.com"));
    }

}
