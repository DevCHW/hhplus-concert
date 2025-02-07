package kr.hhplus.be.server.scheduler.concert

import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ConcertCacheEvictScheduler {

    @CacheEvict(value = ["popular-concerts"], allEntries = true)
    @Scheduled(cron = "0 0 0 * * ?")
    fun evictPopularConcerts() {
    }

}