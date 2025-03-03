package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.support.component.lock.LockClient
import kr.hhplus.be.server.domain.support.component.lock.LockHandler
import kr.hhplus.be.server.domain.support.component.lock.LockStrategy
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import java.util.concurrent.TimeUnit

class RedissonLockClient(
    private val redissonClient: RedissonClient,
) : LockClient {

    override fun isSupport(strategy: LockStrategy): Boolean {
        return strategy == LockStrategy.REDIS_PUB_SUB
    }

    override fun tryLock(
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