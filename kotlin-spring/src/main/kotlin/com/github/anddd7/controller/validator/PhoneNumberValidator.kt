package com.github.anddd7.controller.validator

import com.github.anddd7.controller.PhoneNumber
import org.slf4j.LoggerFactory
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PhoneNumberValidator::class])
annotation class CheckPhoneNumber(
    val message: String = "Phone number is invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class PhoneNumberValidator : ConstraintValidator<CheckPhoneNumber, PhoneNumber> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun isValid(value: PhoneNumber, context: ConstraintValidatorContext): Boolean {
        logger.debug("PhoneNumberValidator")

        return when (value.areaCode) {
            "86" -> {
                if (value.number.length != 11) {
                    context.buildConstraintViolationWithTemplate("请输入合法的中国区手机号码: 11位").addConstraintViolation()
                    false
                } else true
            }
            else -> true
        }
    }
}