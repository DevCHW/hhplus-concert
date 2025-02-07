package kr.hhplus.be.server.infra.storage.redis.support

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.queue.repository.TokenRedisRepository
import kr.hhplus.be.server.infra.storage.redis.WaitingQueueRedisRepositoryImpl
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalRedisInit(
    private val tokenRedisRepository: TokenRedisRepository,
    private val waitingQueueRedisRepository: WaitingQueueRedisRepositoryImpl,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        val userIds = mutableListOf<String>()
        for(i in 1..100) {
            userIds.add(TsidCreator.getTsid().toString())
        }
        userIds.forEach {
            tokenRedisRepository.createToken(it)
                ?.let { waitingQueueRedisRepository.addToken(it) }
        }

        Thread.sleep(5000)
        tokenRedisRepository.createToken(TsidCreator.getTsid().toString())
            ?.let { waitingQueueRedisRepository.addToken(it) }
    }
}