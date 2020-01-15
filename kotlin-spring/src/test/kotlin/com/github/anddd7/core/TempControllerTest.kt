package com.github.anddd7.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.anddd7.core.api.TempController
import com.github.anddd7.core.service.TempService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@ActiveProfiles("test")
@WebMvcTest(TempController::class, excludeAutoConfiguration = [SecurityAutoConfiguration::class])
internal class TempControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockkBean
    private lateinit var tempService: TempService

    private val objectMapper = ObjectMapper()

    @Test
    fun shouldReturnVersion() {
        every { tempService.getVersion() } returns "v0.0.1"

        mvc.perform(get("/temp/version"))
                .andExpect(content().string("v0.0.1"))
    }

    @Test
    fun shouldReturnHealthyInfo() {
        every { tempService.getVersion() } returns "v0.0.1"

        mvc.perform(get("/temp/ping"))
                .andExpect(jsonPath("$.version").value("v0.0.1"))
                .andExpect(jsonPath("$.active").value("ok"))
    }

    @Test
    fun shouldPassValidationOfRequestParametersAndBody() {
        val phone = HashMap<String, Any>()
        phone["areaCode"] = "86"
        phone["number"] = "12345678901"

        val userInfo = HashMap<String, Any>()
        userInfo["name"] = "and777"
        userInfo["age"] = 10
        userInfo["email"] = "liaoad_space@sina.com"
        userInfo["phone"] = phone

        val moreDescription = HashMap<String, Any>()
        moreDescription["title"] = "title"
        moreDescription["content"] = "more description should more than 10"

        val ranges = HashMap<String, Int>()
        ranges["first"] = 1
        ranges["second"] = 2

        val requestBody = HashMap<String, Any>()
        requestBody["userInfo"] = userInfo
        requestBody["moreDescription"] = moreDescription
        requestBody["ranges"] = listOf<Map<String, Int>>(ranges)

        mvc.perform(
                post("/temp/validate")
                        .param("correlationId", "AB12976551827EH1")
                        .param("operations", "validate", "save", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().isOk)
    }

    @Test
    fun shouldGotValidationError_WhileRequestParametersOrBodyIsInvalid() {
        val phone = HashMap<String, Any>()
        phone["areaCode"] = "0"
        phone["number"] = "1"

        val userInfo = HashMap<String, Any>()
        userInfo["name"] = ""
        userInfo["age"] = 8
        userInfo["email"] = "liaoad_space"
        userInfo["phone"] = phone

        val moreDescription = HashMap<String, Any>()
        moreDescription["title"] = ""
        moreDescription["content"] = ""

        val ranges = HashMap<String, Int>()
        ranges["first"] = 3
        ranges["second"] = 2

        val requestBody = HashMap<String, Any>()
        requestBody["userInfo"] = userInfo
        requestBody["moreDescription"] = moreDescription
        requestBody["ranges"] = listOf<Map<String, Int>>(ranges)

        mvc.perform(
                post("/temp/validate")
                        .param("correlationId", "")
                        .param("operations", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().isBadRequest)
                // moreDescription, name, age, email, phone, phone.areaCode, phone.number
                .andExpect(jsonPath("$.errors.length()").value(8))
                .andDo { result -> println(result.response.contentAsString) }
        /*
       [
         "userInfo.age: must be between 9 and 99",
         "userInfo.email: must be a well-formed email address",
         "moreDescription: At least one of title and content should be non-empty",
         "userInfo.phone.areaCode: must match \"(-)?\\d{2,3}\"",
         "userInfo.name: must not be blank",
         "userInfo: username can't be null or `unknown`",
         "userInfo.phone.number: size must be between 6 and 20",
         "ranges[0]: Range is invalid"
       ]
       */
    }
}
