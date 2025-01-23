package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.lock.DistributedLockClient
import kr.hhplus.be.server.domain.lock.ReleaseLock
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import java.util.concurrent.TimeUnit

class RedissonLockClient(
    private val redissonClient: RedissonClient,
) : DistributedLockClient {

    /**
     * 잠금
     */
    override fun getLock(
        key: String,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit
    ): ReleaseLock? {
        val rLock: RLock = redissonClient.getLock(key)
        val isLockAcquired = rLock.tryLock(waitTime, leaseTime, timeUnit)
        if (!isLockAcquired) {
            return null
        }

        return object : ReleaseLock {
            override fun release() {
                if (rLock.isLocked && rLock.isHeldByCurrentThread) {
                    rLock.unlock()
                }
            }
        }
    }

    /**
     * 잠금 해제
     */
    override fun releaseLock(
        lock: ReleaseLock
    ) {
        lock.release()
    }

}