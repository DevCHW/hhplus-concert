package kr.hhplus.be.server.domain.support.component.lock.aop

import kr.hhplus.be.server.domain.support.component.lock.LockResource
import kr.hhplus.be.server.domain.support.component.lock.LockStrategy
import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val resource: LockResource,
    val key: String,
    val strategy: LockStrategy,
    val waitTime: Long = 5L,
    val leaseTime: Long = 3L,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
)
