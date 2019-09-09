package com.github.anddd7.controller.validator

import com.github.anddd7.controller.RangeData
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target( AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RangeValidator::class])
annotation class CheckRange(
    val message: String = "Range is invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class RangeValidator : ConstraintValidator<CheckRange, RangeData> {
    override fun isValid(value: RangeData?, context: ConstraintValidatorContext?): Boolean {
        value ?: return false
        return value.first <= value.second
    }
}
