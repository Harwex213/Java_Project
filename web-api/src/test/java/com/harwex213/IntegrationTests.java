package com.harwex213;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void accessDeniedTest() throws Exception {
        this.mvc.perform(post("/api/sessions"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void accessNotDeniedTest() throws Exception {
        this.mvc.perform(get("/api/cinemas"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
