package com.github.anddd7.controller

import com.github.anddd7.service.TempService
import org.hibernate.validator.constraints.Range
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@RestController
@RequestMapping("/temp")
@Validated
class TempController(val tempService: TempService) {

    @GetMapping("/version")
    fun version() = tempService.getVersion()

    @GetMapping("/ping")
    fun ping() = mapOf(
        "version" to version(),
        "active" to "ok"
    )

    @PostMapping("/validate")
    fun validate(
        @RequestParam @NotBlank correlationId: String,
        @RequestParam @NotEmpty @Size(max = 5) operations: List<String>,
        @RequestBody @Valid validatedRequest: ValidatedRequest
    ) = validatedRequest
}

data class ValidatedRequest(
    @NotBlank
    val name: String,
    @Range(min = 9, max = 99)
    val age: Int,
    @Email
    val email: String,
    @Valid
    val phone: PhoneNumber
)

data class PhoneNumber(
    @Pattern(regexp = "/d{2,3}")
    val areaCode: Int,
    val number: Int
)
