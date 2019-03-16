package com.github.anddd7;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

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
    phone.put("areaCode", 86);
    phone.put("number", 1234567890);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "and777");
    requestBody.put("age", 10);
    requestBody.put("email", "liaoad_space@sina.com");
    requestBody.put("phone", phone);

    mvc.perform(
        post("/temp/validate")
            .param("correlationId", "AB12976551827EH1")
            .param("operations", "validate", "save", "test")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(requestBody))
    ).andExpect(status().isOk());
  }

  @Test
  void shouldGotValidationErrorWhileRequestParametersOrBodyIsInvalid() throws Exception {
    Map<String, Object> phone = new HashMap<>();
    phone.put("areaCode", 0);
    phone.put("number", -1);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "");
    requestBody.put("age", 9);
    requestBody.put("email", "liaoad_space");
    requestBody.put("phone", phone);

    NestedServletException nestedException = assertThrows(
        NestedServletException.class,
        () -> mvc.perform(
            post("/temp/validate")
                .param("correlationId", "")
                .param("operations", "")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().isBadRequest())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
    );

    assertThat(nestedException.getCause()).isInstanceOf(ConstraintViolationException.class);
  }
}