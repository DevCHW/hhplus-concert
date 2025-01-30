package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.support.lock.DistributedLockClient
import kr.hhplus.be.server.domain.support.lock.LockHandler
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

class LettuceLockClient(
    private val redisTemplate: RedisTemplate<String, String>,
) : DistributedLockClient {

    override fun getLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): LockHandler? {
        val maxWaitTimeMillis = timeUnit.toMillis(waitTime)
        val startTime = System.currentTimeMillis()
        while (true) {
            // 락 획득 시 return
            if (acquireLock(key, leaseTime, timeUnit)) {
                return object : LockHandler {
                    override fun unlock() {
                        releaseLock(key)
                    }
                }
            }

            // 재시도 조건에 부합하지 않는 경우 null 반환
            if (!isRetry(maxWaitTimeMillis, startTime)) {
                return null
            }

            // 100ms 대기 후 재시도
            Thread.sleep(100)
        }
    }

    fun releaseLock(key: String) {
        redisTemplate.delete(key)
    }

    private fun acquireLock(key: String, leaseTime: Long, timeUnit: TimeUnit): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent(key, "lock", leaseTime, timeUnit) ?: false
    }

    private fun isRetry(maxWaitTimeMillis: Long, startTime: Long): Boolean {
        return (maxWaitTimeMillis > 0 && System.currentTimeMillis() - startTime < maxWaitTimeMillis)
    }

}