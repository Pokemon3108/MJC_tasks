package com.epam.esm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.epam.esm.config.DataSourceConfiguration;
import com.epam.esm.config.WebConfig;
import com.epam.esm.exception.DuplicateCertificateException;
import com.epam.esm.exception.NoCertificateException;

@SpringJUnitConfig({DataSourceConfiguration.class, WebConfig.class})
@WebAppConfiguration
@ActiveProfiles(value = "dev")
class CertificateControllerTest {

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
        final String certificateName = "certificatto";
        final String description = "description";
        final BigDecimal price = BigDecimal.valueOf(12.34);
        final String createDate = "2021-04-11T00:00:00";
        final String lastUpdateDate = "2021-04-11T00:00:00";

        mockMvc.perform(get("/certificate/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(certificateName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(price))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createDate").value(createDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastUpdateDate").value(lastUpdateDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags[*].name", Matchers.hasItem("car")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags[*].name", Matchers.hasItem("party")));
    }

    @Test
    void readNoExistingCertificateTest() throws Exception {

        final long id = 20;
        mockMvc.perform(get("/certificate/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(NoCertificateException.class));
    }

    @Test
    void deleteTest() throws Exception {

        final long id = 1;
        mockMvc.perform(delete("/certificate/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deleteNoExistingCertificateTest() throws Exception {

        final long id = 20;
        mockMvc.perform(get("/certificate/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(NoCertificateException.class));
    }

    @Test
    void createCertificateTest() throws Exception {

        String fileName = "newCertificate.json";
        String json = FileReaderHelper.readFile(fileName);
        long generatedId = 7;
        mockMvc.perform(post("/certificate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(generatedId)));
    }

    @Test
    void createExistingCertificateTest() throws Exception {

        String fileName = "existingCertificate.json";
        String json = FileReaderHelper.readFile(fileName);
        mockMvc.perform(post("/certificate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(
                        result -> result.getResolvedException().getClass().equals(DuplicateCertificateException.class));
    }

    @Test
    void updateCertificateTest() throws Exception {

        final long id = 4;
        final String certificateName = "new name";
        final String description = "celebrate your main day";
        final BigDecimal price = BigDecimal.valueOf(102.222);
        final int duration = 16;

        String fileName = "updatedCertificate.json";
        String json = FileReaderHelper.readFile(fileName);
        mockMvc.perform(patch("/certificate")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(certificateName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(price))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(duration))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags", Matchers.hasSize(2)));
    }

    @Test
    void findByNameSortByNameTest() throws Exception {

        int[] ids = new int[]{3, 5, 2};

        mockMvc.perform(get("/certificate?name=new&sortParams=name"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(ids[0]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(ids[1]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").value(ids[2]));
    }

    @Test
    void sortByDateDescTest() throws Exception {

        int[] ids = new int[]{1, 3, 2, 4, 6, 5};

        mockMvc.perform(get("/certificate?sortParams=date&direction=desc"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(ids[0]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(ids[1]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").value(ids[2]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].id").value(ids[3]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[4].id").value(ids[4]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[5].id").value(ids[5]));
    }

}
