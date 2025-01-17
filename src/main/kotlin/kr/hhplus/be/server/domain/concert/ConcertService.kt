package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.Concert
import org.springframework.stereotype.Service

@Service
class ConcertService(
    private val concertRepository: ConcertRepository,
) {

    fun getConcert(concertId: String): Concert {
        return concertRepository.getById(concertId)
    }

    fun getConcerts(): List<Concert> {
        return concertRepository.getAll()
    }
}