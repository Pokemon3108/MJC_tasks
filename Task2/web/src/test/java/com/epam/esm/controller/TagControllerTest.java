package com.epam.esm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.esm.exception.tag.DuplicateTagException;
import com.epam.esm.exception.tag.NoTagException;

@SpringBootTest
@ActiveProfiles(value = "dev")
class TagControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    void readTest() throws Exception {

        final long id = 1;
        final String tagName = "nature";
        mockMvc.perform(get("/tag/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tagName));
    }

    @Test
    void readNoExistingTagTest() throws Exception {

        final long id = 7;
        mockMvc.perform(get("/tag/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(NoTagException.class));
    }

    @Test
    void deleteTest() throws Exception {

        final long id = 1;
        mockMvc.perform(delete("/tag/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deleteNoExistingTagTest() throws Exception {

        final long id = 8;
        mockMvc.perform(get("/tag/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(NoTagException.class));
    }

    @Test
    void createTagTest() throws Exception {

        String fileName = "newTag.json";
        String json = FileReaderHelper.readFile(fileName);
        long generatedId = 7;
        mockMvc.perform(post("/tag")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(generatedId));
    }

    @Test
    void createExistingTagTest() throws Exception {

        String fileName = "existingTag.json";
        String json = FileReaderHelper.readFile(fileName);
        mockMvc.perform(post("/tag")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(DuplicateTagException.class));
    }


}
