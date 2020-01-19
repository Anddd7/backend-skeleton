package com.github.anddd7;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureEmbeddedDatabase(provider = DatabaseProvider.DOCKER)
class TempApiTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void should_Return_Version() throws Exception {
    mvc.perform(get("/temp/version"))
        .andExpect(content().string("v0.0.1"));
  }

  @Test
  void should_Return_Healthy_Info() throws Exception {
    mvc.perform(get("/temp/ping"))
        .andExpect(jsonPath("$.version").value("v0.0.1"))
        .andExpect(jsonPath("$.active").value("ok"));
  }


  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void should_Pass_Validation_Of_Request_Parameters_And_Body() throws Exception {
    Map<String, Object> phone = new HashMap<>();
    phone.put("areaCode", "86");
    phone.put("number", "12345678901");

    Map<String, Object> userInfo = new HashMap<>();
    userInfo.put("name", "and777");
    userInfo.put("age", 10);
    userInfo.put("email", "liaoad_space@sina.com");
    userInfo.put("phone", phone);

    Map<String, Object> moreDescription = new HashMap<>();
    moreDescription.put("title", "title");
    moreDescription.put("content", "more description should more than 10");

    Map<String, Integer> ranges = new HashMap<>();
    ranges.put("first", 1);
    ranges.put("second", 2);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("userInfo", userInfo);
    requestBody.put("moreDescription", moreDescription);
    requestBody.put("ranges", Collections.singletonList(ranges));

    mvc.perform(
        post("/temp/validate")
            .param("correlationId", "AB12976551827EH1")
            .param("operations", "validate", "save", "test")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
    ).andExpect(status().isOk());
  }
}
