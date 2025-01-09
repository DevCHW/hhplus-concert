package kr.hhplus.be.server.domain

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.annotation.PostConstruct
import kr.hhplus.be.server.domain.token.model.Token
import kr.hhplus.be.server.infra.storage.core.jpa.TokenJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class DBInit(
    private val tokenJpaRepository: TokenJpaRepository,
) {

    @PostConstruct
    fun test() {
        val list = mutableListOf<String>()
        for (i in 1..3) {
            val token = tokenJpaRepository.save(
                Token(
                    userId = TsidCreator.getTsid().toString(),
                    token = UUID.randomUUID(),
                )
            )
            list.add("$i 번째 ID = ${token.id}")
        }

        for (i in 4..5) {
            val token = tokenJpaRepository.save(
                Token(
                    userId = TsidCreator.getTsid().toString(),
                    token = UUID.randomUUID(),
                )
            )
            list.add("$i 번째 ID = ${token.id}")
        }
    }
}