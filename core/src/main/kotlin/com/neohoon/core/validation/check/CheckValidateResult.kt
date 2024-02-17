package com.neohoon.core.validation.check

class CheckValidateResult private constructor(
    val valid: Boolean,
    val message: String = "errors.test",
    val args: Array<out Any> = arrayOf()
) {
    companion object {
        fun success() = CheckValidateResult(true)
        fun failure(message: String, vararg args: Any) = CheckValidateResult(false, message, args)
    }
}