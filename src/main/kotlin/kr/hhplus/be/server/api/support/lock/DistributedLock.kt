package kr.hhplus.be.server.api.support.lock

import kr.hhplus.be.server.domain.lock.DistributedLockStrategy
import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val lockName: String,
    val strategy: DistributedLockStrategy,
    val waitTime: Long = 5L,
    val releaseTime: Long = 3L,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
)
