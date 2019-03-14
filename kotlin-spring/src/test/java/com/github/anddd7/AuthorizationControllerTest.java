package com.github.anddd7;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTesting
public class AuthorizationControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void shouldNotAccessDashboard_WhenUserHaveNotLogin() throws Exception {
    mvc.perform(get("/api/dashboard")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"DASHBOARD"})
  void shouldAccessDashboard_WhenUserLoginWithPermission() throws Exception {
    mvc.perform(get("/api/dashboard")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(authorities = {"DASHBOARD"})
  void shouldNotAccessOrder_WhenUserDontHavePermission() throws Exception {
    mvc.perform(get("/api/order")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"Order"})
  void shouldAccessOrder_WhenUserLoginWithPermission() throws Exception {
    mvc.perform(get("/api/order")).andExpect(status().isForbidden());
  }
}