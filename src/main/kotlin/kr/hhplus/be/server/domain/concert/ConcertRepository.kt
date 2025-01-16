package kr.hhplus.be.server.domain.concert

import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.concert.model.CreateConcert

interface ConcertRepository {
    fun getById(concertId: String): Concert

    fun save(createConcert: CreateConcert): Concert
}