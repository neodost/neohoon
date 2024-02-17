package com.neohoon.core.validation.check

import com.neohoon.core.message.MessageResolver
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CheckValidator(
    private val messageResolver: MessageResolver
) : ConstraintValidator<Check, CheckValidatable> {

    override fun isValid(value: CheckValidatable?, context: ConstraintValidatorContext?): Boolean {

        val validResult = value?.validate() ?: CheckValidateResult.success()

        return validResult.valid.also {
            if (!it) {
                context?.disableDefaultConstraintViolation()
                context?.buildConstraintViolationWithTemplate(
                    messageResolver.getMessage(validResult.message, *validResult.args)
                )?.addConstraintViolation()
            }
        }
    }
}