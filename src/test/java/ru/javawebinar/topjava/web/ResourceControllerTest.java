package ru.javawebinar.topjava.web;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void testCSS() throws Exception
    {
        mockMvc.perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType("text/css"));
    }


}
