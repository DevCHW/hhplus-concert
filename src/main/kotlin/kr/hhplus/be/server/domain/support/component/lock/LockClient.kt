package kr.hhplus.be.server.domain.support.component.lock

import java.util.concurrent.TimeUnit

interface LockClient {
    fun isSupport(strategy: LockStrategy): Boolean
    fun tryLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): LockHandler?
}