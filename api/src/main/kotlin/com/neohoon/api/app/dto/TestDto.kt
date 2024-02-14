package com.neohoon.api.app.dto

import com.neohoon.core.validation.check.CheckValidatable
import com.neohoon.core.validation.check.CheckValidateResult

data class TestDto(
    val id: String,
    val name: String
) : CheckValidatable {

    override fun validate(): CheckValidateResult {
        if (id == name) {
            return CheckValidateResult.failure("errors.test", "arg1", "arg2", 3)
        }

        return super.validate()
    }

}