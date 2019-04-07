package com.github.anddd7;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTesting
class TempControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void shouldReturnVersion() throws Exception {
    mvc.perform(get("/temp/version"))
        .andExpect(content().string("v0.0.1"));
  }

  @Test
  void shouldReturnHealthyInfo() throws Exception {
    mvc.perform(get("/temp/ping"))
        .andExpect(jsonPath("$.version").value("v0.0.1"))
        .andExpect(jsonPath("$.active").value("ok"));
  }


  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldPassValidationOfRequestParametersAndBody() throws Exception {
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

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("userInfo", userInfo);
    requestBody.put("moreDescription", moreDescription);

    mvc.perform(
        post("/temp/validate")
            .param("correlationId", "AB12976551827EH1")
            .param("operations", "validate", "save", "test")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(requestBody))
    ).andExpect(status().isOk());
  }

  @Test
  void shouldGotValidationError_WhileRequestParametersOrBodyIsInvalid() throws Exception {
    Map<String, Object> phone = new HashMap<>();
    phone.put("areaCode", "0");
    phone.put("number", "1");

    Map<String, Object> userInfo = new HashMap<>();
    userInfo.put("name", "");
    userInfo.put("age", 8);
    userInfo.put("email", "liaoad_space");
    userInfo.put("phone", phone);

    Map<String, Object> moreDescription = new HashMap<>();
    moreDescription.put("title", "");
    moreDescription.put("content", "");

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("userInfo", userInfo);
    requestBody.put("moreDescription", moreDescription);

    mvc.perform(
        post("/temp/validate")
            .param("correlationId", "")
            .param("operations", "")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(requestBody))
    ).andExpect(status().isBadRequest())
        // moreDescription, name, age, email, phone, phone.areaCode, phone.number
        .andExpect(jsonPath("$.errors.length()").value(7))
        .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    /*
    [
      "userInfo.age: must be between 9 and 99",
      "userInfo.email: must be a well-formed email address",
      "moreDescription: At least one of title and content should be non-empty",
      "userInfo.phone.areaCode: must match \"(-)?\\d{2,3}\"",
      "userInfo.name: must not be blank",
      "userInfo: username can't be null or `unknown`",
      "userInfo.phone.number: size must be between 6 and 20"
    ]
    */
  }
}