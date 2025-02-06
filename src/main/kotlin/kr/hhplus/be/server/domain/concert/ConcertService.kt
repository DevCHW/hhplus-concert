package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository
import org.springframework.stereotype.Service

@Service
class ConcertService(
    private val concertRepository: ConcertRepository,
) {

    /**
     * 콘서트 단건 조회
     */
    fun getConcert(concertId: String): Concert {
        return concertRepository.getById(concertId)
    }

    /**
     * 콘서트 목록 조회
     */
    fun getConcerts(): List<Concert> {
        return concertRepository.getAll()
    }

    fun getConcertsByIds(concertIds: Set<String>): List<Concert> {
        return concertRepository.getByIds(concertIds)
    }
}