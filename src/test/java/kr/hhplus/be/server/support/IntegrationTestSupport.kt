package kr.hhplus.be.server.support

import io.hhplus.cleanarchitecture.support.database.AfterDatabaseCleanUp
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor


@SpringBootTest
@AfterDatabaseCleanUp
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class IntegrationTestSupport