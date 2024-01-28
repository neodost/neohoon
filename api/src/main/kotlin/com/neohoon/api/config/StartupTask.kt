package com.neohoon.api.config

import com.neohoon.api.app.repository.TestRepository
import com.neohoon.domain.test.Test
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class StartupTask(
    private val testRepository: TestRepository
) : ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        testRepository.deleteAll()
        testRepository.flush()
        testRepository.saveAll(
            (1..100).map {
                Test("${Char(0x41 + Random.nextInt(26))}")
            }
        )
    }
}