package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.support.lock.DistributedLockClient
import kr.hhplus.be.server.domain.support.lock.LockHandler
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import java.util.concurrent.TimeUnit

class RedissonLockClient(
    private val redissonClient: RedissonClient,
) : DistributedLockClient {

    override fun getLock(
        key: String,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit
    ): LockHandler? {
        val rLock: RLock = redissonClient.getLock(key)
        val isLockAcquired = rLock.tryLock(waitTime, leaseTime, timeUnit)
        if (!isLockAcquired) {
            return null
        }

        return object : LockHandler {
            override fun unlock() {
                releaseLock(rLock)
            }
        }
    }

    private fun releaseLock(rLock: RLock) {
        if (rLock.isLocked && rLock.isHeldByCurrentThread) {
            rLock.unlock()
        }
    }

}