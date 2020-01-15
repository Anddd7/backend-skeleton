package com.github.anddd7.core.api

import com.github.anddd7.core.api.validator.CheckMoreDescription
import com.github.anddd7.core.api.validator.CheckPhoneNumber
import com.github.anddd7.core.api.validator.CheckRange
import com.github.anddd7.core.api.validator.CheckUserInfo
import com.github.anddd7.core.service.TempService
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

    /**
     * Required request parameter will always be checked, in `MethodValidationInterceptor`
     * - `MethodValidationInterceptor` is also used to validate method-level parameters
     * Request body will be checked in `RequestResponseBodyMethodProcessor`
     * - Only validate model if it's marked @Valid
     */
    @PostMapping("/validate")
    fun validate(
        @RequestParam @NotBlank correlationId: String,
        @RequestParam @NotEmpty @Size(max = 5) operations: List<String>,
        @RequestBody @Valid request: ValidateRequest
    ) = request
}

/**
 * Constraint annotation can used for value-parameter/field/class
 * - @RequestBody can't apply value-parameter level annotation at same time
 * |  - Wrap your model in a request-body-wrapper, add validation annotation on field
 * |  - Reuse validation annotation as value-parameter level in service
 * - Validation annotation only validate field/parameter self, won't do validation for nested fields
 * |  - @Valid is telling validator the model have to validate
 * |  - @ValidationAnnotation is telling validator how to validate
 * |  - You need both of them to validate a complex model with nested sub models
 */
data class ValidateRequest(
    @field:Valid // Need to validate sub fields in this model
    @field:CheckUserInfo // Call `UserInfoValidator` to validate this
    val userInfo: UserInfo,
    @field:CheckMoreDescription
    val moreDescription: MoreDescription,
    @field:Valid
    val ranges: List<RangeData>
)

data class UserInfo(
    @field:NotBlank
    val name: String,
    @field:Range(min = 9, max = 99)
    val age: Int,
    @field:Email
    val email: String,
    @field:Valid
    @field:CheckPhoneNumber
    val phone: PhoneNumber
)

data class PhoneNumber(
    @field:Pattern(regexp = "(-)?\\d{2,3}")
    val areaCode: String,
    @field:Size(min = 6, max = 20)
    val number: String
)

data class MoreDescription(
    val title: String,
    val content: String
)

@CheckRange
data class RangeData(val first: Int, val second: Int)
