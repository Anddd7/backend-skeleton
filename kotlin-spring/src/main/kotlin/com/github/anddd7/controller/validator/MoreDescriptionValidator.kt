package com.github.anddd7.controller.validator

import com.github.anddd7.controller.MoreDescription
import org.slf4j.LoggerFactory
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MoreDescriptionValidator::class])
annotation class CheckMoreDescription(
    val message: String = "Input more description is invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@SuppressWarnings("MagicNumber")
class MoreDescriptionValidator : ConstraintValidator<CheckMoreDescription, MoreDescription> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun isValid(value: MoreDescription, context: ConstraintValidatorContext): Boolean {
        logger.debug("MoreDescriptionValidator")
        context.disableDefaultConstraintViolation()

        return when {
            value.title.isEmpty() && value.content.isEmpty() -> {
                context.addConstraintViolation("At least one of title and content should be non-empty")
                false
            }
            value.content.isNotEmpty() && value.content.length !in 10..500 -> {
                context.addConstraintViolation("Content is limit 10-500 characters")
                false
            }
            else -> true
        }
    }
}

fun ConstraintValidatorContext.addConstraintViolation(
    template: String,
    modify: ((ConstraintViolationBuilder) -> Unit)? = null
): ConstraintValidatorContext {
    val builder = buildConstraintViolationWithTemplate(template)
    modify?.invoke(builder)
    return builder.addConstraintViolation()
}
