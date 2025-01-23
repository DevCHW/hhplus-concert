package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.lock.DistributedLockClient
import kr.hhplus.be.server.domain.lock.LockResourceManager
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

class LettuceLockClient(
    private val redisTemplate: RedisTemplate<String, String>,
) : DistributedLockClient {
    override fun getLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): LockResourceManager? {
        val maxWaitTimeMillis = timeUnit.toMillis(waitTime)
        val startTime = System.currentTimeMillis()
        while (true) {
            // 락 획득 시 return
            if (acquireLock(key, leaseTime, timeUnit)) {
                return object : LockResourceManager {
                    override fun unlock() {
                        releaseLock(key)
                    }
                }
            }

            // 더 이상 재시도할 시간이 없다면 종료
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