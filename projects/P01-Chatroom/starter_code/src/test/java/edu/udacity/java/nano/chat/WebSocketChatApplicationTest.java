package edu.udacity.java.nano.chat;

import edu.udacity.java.nano.WebSocketChatApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(WebSocketChatApplication.class)
public class WebSocketChatApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHomeViewMapping() throws Exception{
        mvc.perform(get("/")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(view().name("/login"));
    }

    @Test
    public void testIndexViewMapping() throws Exception{
        mvc.perform(get("/index")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(view().name("/chat"));
    }
}
