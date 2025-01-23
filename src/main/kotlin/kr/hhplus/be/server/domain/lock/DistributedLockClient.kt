package kr.hhplus.be.server.domain.lock

import java.util.concurrent.TimeUnit

interface DistributedLockClient {

    fun getLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): ReleaseLock?

    fun releaseLock(lock: ReleaseLock)
}