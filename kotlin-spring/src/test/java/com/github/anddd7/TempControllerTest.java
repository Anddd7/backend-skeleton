package com.github.anddd7;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureEmbeddedDatabase
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