package kr.hhplus.be.server.domain.support.lock.aop

import kr.hhplus.be.server.domain.support.lock.LockStrategy
import kr.hhplus.be.server.domain.support.lock.LockResource
import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val resource: LockResource,
    val key: String,
    val strategy: LockStrategy,
    val waitTime: Long = 5L,
    val releaseTime: Long = 3L,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
)
