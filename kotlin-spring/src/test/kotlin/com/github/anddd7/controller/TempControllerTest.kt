package com.github.anddd7.controller

import com.github.anddd7.service.TempService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@ActiveProfiles("test")
@WebMvcTest(controllers = [TempController::class])
internal class TempControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var tempService: TempService

    @BeforeEach
    fun setUp() {
        given(tempService.getVersion()).willReturn("v0.0.1")
    }

    @Test
    fun `should return version`() {
        mvc.perform(get("/temp/version"))
            .andExpect(content().string("v0.0.1"))
    }

    @Test
    fun `should return healthy info`() {
        mvc.perform(get("/temp/ping"))
            .andExpect(jsonPath("$.version").value("v0.0.1"))
            .andExpect(jsonPath("$.active").value("ok"))
    }
}