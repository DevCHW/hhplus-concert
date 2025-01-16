package kr.hhplus.be.server.domain.token

import kr.hhplus.be.server.domain.token.model.CreateToken
import kr.hhplus.be.server.domain.token.model.Token
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class TokenService(
    private val repository: TokenRepository,
) {

    /**
     * 토큰 생성
     */
    fun createToken(userId: String): Token {
        return repository.save(
            CreateToken(
                userId = userId,
                token = UUID.randomUUID(),
            )
        )
    }

    /**
     * 토큰 조회
     */
    fun getToken(token: UUID): Token {
        return repository.getByToken(token)
    }

    /**
     * 활성 토큰 만료
     */
    fun expireActiveTokens(activeTokenLifeTime: Long, now: LocalDateTime) {
        val activeTokens = repository.getTokenByStatus(Token.Status.ACTIVE)
        val expiredActiveTokens = activeTokens
            .filter {
                it.updatedAt.plusSeconds(activeTokenLifeTime).isBefore(now)
            }

        if (expiredActiveTokens.isNotEmpty()) {
            val expiredTokenIds = expiredActiveTokens.map { it.id }
            repository.deleteByIds(expiredTokenIds)
        }
    }


    /**
     * 비활성 토큰 목록 활성 상태로 변경
     */
    fun ActivateTokens(activeTokenMaxSize: Int) {
        // 활성 상태 토큰 목록 조회
        val activeTokens = repository.getTokenByStatus(Token.Status.ACTIVE)

        // 활성 상태로 업데이트 할 갯수 계산
        val targetCount = activeTokenMaxSize - activeTokens.size
        if (targetCount <= 0) return

        // 활성 상태로 업데이트 할 비활성 토큰 목록 생성 순서(ID) 오름차순 정렬 조회
        val inactiveTokens = repository.getTokenByStatusNotSortByIdAsc(Token.Status.ACTIVE, targetCount)
        if (inactiveTokens.isEmpty()) return

        // 활성 상태로 업데이트 할 토큰 ID 목록 추출
        val inactiveTokenIds = inactiveTokens.map { it.id }

        // 추출한 ID 목록을 통해 ACTIVE 상태로 업데이트
        val result = repository.updateStatusByIdsIn(Token.Status.ACTIVE, inactiveTokenIds)
    }

    /**
     * 토큰 삭제
     */
    fun deleteToken(token: UUID) {
        repository.deleteByToken(token)
    }
}