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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.epam.esm.exception.certificate.DuplicateCertificateException;
import com.epam.esm.exception.certificate.NoCertificateException;


@Transactional
@SpringBootTest
@ActiveProfiles(value = "dev")
class CertificateControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

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

        mockMvc.perform(get("/certificates/{id}", id))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags[*].name", Matchers.hasItem("nature")));
    }

    @Test
    void readNoExistingCertificateTest() throws Exception {

        final long id = 20;
        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(NoCertificateException.class));
    }

    @Test
    void deleteTest() throws Exception {

        final long id = 1;
        mockMvc.perform(delete("/certificates/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deleteNoExistingCertificateTest() throws Exception {

        final long id = 20;
        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(result -> result.getResolvedException().getClass().equals(NoCertificateException.class));
    }

    @Test
    void createCertificateTest() throws Exception {

        String fileName = "newCertificate.json";
        String json = FileReaderHelper.readFile(fileName);
        long generatedId = 7;
        mockMvc.perform(post("/certificates")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(generatedId));
    }

    @Test
    void createExistingCertificateTest() throws Exception {

        String fileName = "existingCertificate.json";
        String json = FileReaderHelper.readFile(fileName);
        mockMvc.perform(post("/certificates")
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
        mockMvc.perform(patch("/certificates/{id}", id)
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

        mockMvc.perform(get("/certificates?name=new&sortParams=name"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[0].id").value(ids[0]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[1].id").value(ids[1]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[2].id").value(ids[2]));
    }

    @Test
    void findByTagTest() throws Exception {

        int[] ids = new int[]{1, 5};

        mockMvc.perform(get("/certificates?tags=car"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.certificateModels.content.*", Matchers.hasSize(ids.length)))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.certificateModels.content.[*].id", Matchers.hasItem(ids[0])))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.certificateModels.content.[*].id", Matchers.hasItem(ids[1])));
    }

    @Test
    void sortByDateDescTest() throws Exception {

        int[] ids = new int[]{1, 3, 2, 4, 6};

        mockMvc.perform(get("/certificates?sortParams=createDate&direction=desc"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[0].id").value(ids[0]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[1].id").value(ids[1]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[2].id").value(ids[2]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[3].id").value(ids[3]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.certificateModels.content.[4].id").value(ids[4]));
    }

}
