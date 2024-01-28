package com.neohoon.api.config

import com.neohoon.api.app.repository.TestRepository
import com.neohoon.api.app.repository.TestSubDataRepository
import com.neohoon.api.app.repository.TestSubRepository
import com.neohoon.domain.test.Test
import com.neohoon.domain.test.TestSub
import com.neohoon.domain.test.TestSubData
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class StartupTask(
    private val testRepository: TestRepository,
    private val testSubRepository: TestSubRepository,
    private val testSubDataRepository: TestSubDataRepository
) : ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        testSubDataRepository.deleteAll()
        testSubRepository.deleteAll()
        testRepository.deleteAll()
        testRepository.flush()

        (1..100).forEach {
            val test = Test("${Char(0x41 + Random.nextInt(26))}")
            testRepository.save(test)
            (1..10).forEach {
                val testSub = TestSub(test, "${Char(0x41 + Random.nextInt(26))}")
                val testSubData = TestSubData(testSub, UUID.randomUUID().toString())
                testSubRepository.save(testSub)
                testSubDataRepository.save(testSubData)
            }
        }
    }
}