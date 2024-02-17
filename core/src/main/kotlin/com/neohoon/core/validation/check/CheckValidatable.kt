package com.neohoon.core.validation.check

interface CheckValidatable {

    fun validate(): CheckValidateResult = CheckValidateResult.success()

}