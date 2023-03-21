package com.fptacademy.training.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProgramResourceTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    private String token;
//    @BeforeEach
//    public void getAccessToken() throws Exception {
//        MvcResult result = mockMvc.perform(post("/api/auth/login")
//                        .header("Content-Type", "application/json")
//                        .content("{\n" +
//                                "  \"email\": \"admin@gmail.com\",\n" +
//                                "  \"password\": \"12345\"\n" +
//                                "}"))
//                .andExpect(status().isOk())
//                .andReturn();
//        token = "Bearer " + result.getResponse().getHeader("access_token");
//    }
//    @Test
//    public void TestDeleteProgram() throws Exception {
//        MvcResult result = mockMvc.perform(post("/api/programs")
//                        .header("Authorization", token)
//                        .header("Content-Type", "application/json")
//                        .content("{\"name\":\"test\", \"syllabusIds\":[]}"))
//                .andExpect(status().isCreated())
//                .andReturn();
//        String json = result.getResponse().getContentAsString();
//        JsonObject responseObject = JsonParser.parseString(json).getAsJsonObject();
//        Long id = responseObject.get("id").getAsLong();
//        mockMvc.perform(delete("/api/programs/{id}", id)
//                        .header("Authorization", token))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void TestDeleteProgramNotFound() throws Exception {
//        mockMvc.perform(delete("/api/programs/{id}", 999)
//                        .header("Authorization", token))
//                .andExpect(status().isNotFound());
//    }
}
