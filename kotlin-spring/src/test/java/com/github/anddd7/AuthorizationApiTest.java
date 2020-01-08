package com.github.anddd7;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureEmbeddedDatabase(provider = DatabaseProvider.DOCKER)
public class AuthorizationApiTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void should_not_access_dashboard_when_user_have_not_login() throws Exception {
    mvc.perform(get("/api/dashboard")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"DASHBOARD"})
  void should_Access_Dashboard_When_User_Login_With_Permission() throws Exception {
    mvc.perform(get("/api/dashboard")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(authorities = {"DASHBOARD"})
  void should_Not_Access_Order_When_User_Dont_Have_Permission() throws Exception {
    mvc.perform(get("/api/order")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"Order"})
  void should_Access_Order_When_User_Login_With_Permission() throws Exception {
    mvc.perform(get("/api/order")).andExpect(status().isForbidden());
  }
}
