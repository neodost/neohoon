package com.neohoon.auth.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(basePackages = [
    "com.neohoon.domain.entity"
])
class JpaConfig {
}