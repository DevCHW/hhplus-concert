package kr.hhplus.be.server.domain.support.lock

import java.util.concurrent.TimeUnit

interface DistributedLockClient {
    fun tryLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): LockHandler?
}