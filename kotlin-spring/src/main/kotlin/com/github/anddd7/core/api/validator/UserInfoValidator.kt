package com.github.anddd7.controller.validator

import com.github.anddd7.controller.UserInfo
import org.slf4j.LoggerFactory
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UserInfoValidator::class])
annotation class CheckUserInfo(
    val message: String = "Input user information is invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class UserInfoValidator : ConstraintValidator<CheckUserInfo, UserInfo> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun isValid(value: UserInfo, context: ConstraintValidatorContext): Boolean {
        logger.debug("UserInfoValidator")

        if (value.name.isEmpty() || value.name == "unknown") {
            // disable default & add customized error message
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("username can't be null or `unknown`")
                .addConstraintViolation()
            return false
        }
        return true
    }
}
