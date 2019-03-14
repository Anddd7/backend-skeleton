package com.github.anddd7;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
}