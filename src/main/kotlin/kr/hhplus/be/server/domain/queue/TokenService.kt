package kr.hhplus.be.server.domain.queue

import kr.hhplus.be.server.domain.queue.repository.TokenRedisRepository
import kr.hhplus.be.server.domain.support.error.CoreException
import kr.hhplus.be.server.domain.support.error.ErrorType
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val tokenRedisRepository: TokenRedisRepository,
) {

    // 유저 ID로 토큰 생성
    fun generateToken(userId: String): UUID {
        return tokenRedisRepository.createToken(userId) ?: throw CoreException(ErrorType.TOKEN_ALREADY_EXIST)
    }

    fun removeTokens(tokens: Set<UUID>) {
        tokenRedisRepository.removeTokens(tokens)
    }

    fun remove(userId: String) {
        tokenRedisRepository.remove(userId)
    }
}