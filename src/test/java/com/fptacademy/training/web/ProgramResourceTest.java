package com.fptacademy.training.web;

import com.fptacademy.training.repository.ProgramRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProgramResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProgramRepository programRepository;
    private static final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJhdXRoIjoiQ2xhc3NfRnVsbEFjY2VzcyxNYXRlcmlhbF9GdWxsQWNjZXNzLFByb2dyYW1fRnVsbEFjY2VzcyxTeWxsYWJ1c19GdWxsQWNjZXNzLFVzZXJfRnVsbEFjY2VzcyIsImV4cCI6MTY3ODk1OTcyNn0.SaWGkjSJW0iPbhJrsMqgu162GN3Y7cVfEMRkBQBiCfw";
    @Test
    public void TestDeleteProgram() throws Exception {
        MvcResult result =  mockMvc.perform(post("/api/programs")
                        .header("Authorization", token)
                        .header("Content-Type","application/json")
                        .content("{\"name\":\"test\", \"syllabusIds\":[]}"))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        JsonObject responseObject = JsonParser.parseString(json).getAsJsonObject();
        Long id = responseObject.get("id").getAsLong();
        mockMvc.perform(delete("/api/programs/{id}", id)
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }
    @Test
    public void TestDeleteProgramNotFound() throws Exception{
        mockMvc.perform(delete("/api/programs/{id}", 999)
                        .header("Authorization", token))
                .andExpect(status().isNotFound());
    }
}
