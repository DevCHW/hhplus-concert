package kr.hhplus.be.server.domain.support.component.lock

import java.util.concurrent.TimeUnit

interface LockClient {
    fun tryLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): LockHandler?
}