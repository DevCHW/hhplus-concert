package kr.hhplus.be.server.domain.queue.repository

import java.util.*

interface TokenRedisRepository {
    /**
     * 사용자 ID에 대한 새로운 토큰을 생성합니다.
     * 해당 사용자의 토큰이 이미 존재하는 경우 생성하지 않습니다.
     *
     * @param userId 토큰을 생성할 사용자 ID
     * @return 생성된 UUID 토큰, 실패시 null 반환
     */
    fun createToken(userId: String): UUID?

    /**
     * 지정된 토큰들을 일괄 삭제합니다.
     * Redis에서 모든 토큰을 검색하여 일치하는 항목을 제거합니다.
     *
     * @param tokens 삭제할 UUID 토큰 집합
     */
    fun removeTokens(tokens: Set<UUID>)

    /**
     * 특정 사용자의 토큰을 삭제합니다.
     *
     * @param userId 토큰을 삭제할 사용자 ID
     */
    fun remove(userId: String)

    /**
     * 사용자 ID에 해당하는 토큰을 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 사용자의 UUID 토큰, 없는 경우 null 반환
     */
    fun getNullableToken(userId: String): UUID?

}
