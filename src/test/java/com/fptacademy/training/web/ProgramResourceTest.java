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
    private static final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraGFuaEBnbWFpbC5jb20iLCJhdXRoIjoiJ0NsYXNzIEFkbWluJywnU3lsbGFidXNfQ3JlYXRlLCdTeWxsYWJ1c19GdWxsQWNjZXNzLCdUcmFpbmVyJywoMiwoMyxDbGFzLENsYXNzX0Z1bGxBY2Nlc3MsTWF0ZXJpYWxfRnVsbEFjY2VzcyxQcm9ncmFtX0Z1bGxBY2Nlc3MsUHJvZ3JhbV9WaWV3LFN5bGxhYnVzX0Z1bGxBY2Nlc3MsVXNlcl9BY2Nlc3NEZW5pZWQnKSxVc2VyX0Z1bGxBY2Nlc3MnKSIsImV4cCI6MTY3ODc3MDMzM30.dTHSKMw7rA7aB2lKVd2z8Gi78LRYFhj0inKpedhIfc4";
//    @Test
//    public void TestDeleteProgram() throws Exception {
//        MvcResult result =  mockMvc.perform(post("/api/programs")
//                .header("Authorization", token)
//                        .header("Content-Type","application/json")
//                .content("{\"name\":\"test\", \"syllabusIds\":[]}"))
//                        .andExpect(status().isCreated())
//                .andReturn();
//        String json = result.getResponse().getContentAsString();
//        JsonObject responseObject = JsonParser.parseString(json).getAsJsonObject();
//        Long id = responseObject.get("id").getAsLong();
//        mockMvc.perform(delete("/api/programs/{id}", id)
//                        .header("Authorization", token))
//                .andExpect(status().isOk());
//    }
//    @Test
//    public void TestDeleteProgramNotFound() throws Exception{
//        mockMvc.perform(delete("/api/programs/{id}", 999)
//                        .header("Authorization", token))
//                .andExpect(status().isNotFound());
//    }
}
