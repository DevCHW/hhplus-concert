package kr.hhplus.be.server.support.database

import org.springframework.test.context.TestExecutionListeners

@Retention(AnnotationRetention.RUNTIME)
@TestExecutionListeners(
    value = [AfterDatabaseCleanUpTestExecutionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
annotation class AfterDatabaseCleanUp
