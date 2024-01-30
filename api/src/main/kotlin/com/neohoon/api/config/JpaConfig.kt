package com.neohoon.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(basePackages = [
    "com.neohoon.domain.entity"
])
class JpaConfig {
}