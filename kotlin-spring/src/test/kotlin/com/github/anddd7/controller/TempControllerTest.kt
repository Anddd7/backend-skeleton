package com.github.anddd7.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.anddd7.service.TempService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.util.NestedServletException
import javax.validation.ConstraintViolationException

@ActiveProfiles("test")
@WebMvcTest(controllers = [TempController::class], secure = false)
internal class TempControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var tempService: TempService

    private val jacksonObjectMapper = jacksonObjectMapper()

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

    @Test
    fun `should pass validation of request parameters and body`() {
        mvc.perform(
            post("/temp/validate")
                .param("correlationId", "AB12976551827EH1")
                .param("operations", "validate", "save", "test")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    jacksonObjectMapper.writeValueAsString(
                        mapOf(
                            "name" to "and777",
                            "age" to 10,
                            "email" to "liaoad_space@sina.com",
                            "phone" to mapOf(
                                "areaCode" to 86,
                                "number" to 1234567890
                            )
                        )
                    )
                )
        ).andExpect(status().isOk)
    }

    @Test
    fun `should got failed while validate request parameters and body`() {
        val nestedException = assertThrows<NestedServletException> {
            mvc.perform(
                post("/temp/validate")
                    .param("correlationId", "")
                    .param("operations", "")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(
                        jacksonObjectMapper.writeValueAsString(
                            mapOf(
                                "name" to "",
                                "age" to 9,
                                "email" to "liaoad_space",
                                "phone" to mapOf(
                                    "areaCode" to 0,
                                    "number" to -1
                                )
                            )
                        )
                    )
            ).andExpect(status().isBadRequest)
                .andDo { println(it.response.contentAsString) }
        }

        assertThat(nestedException.cause).isInstanceOf(ConstraintViolationException::class.java)
    }
}