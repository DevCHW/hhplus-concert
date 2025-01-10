package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.Concert

interface ConcertRepository {
    fun getById(concertId: String): Concert

    fun save(concert: Concert): Concert
}