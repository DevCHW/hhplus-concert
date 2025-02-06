package kr.hhplus.be.server.domain.concert.repository

import kr.hhplus.be.server.domain.concert.model.Concert
import kr.hhplus.be.server.domain.concert.model.CreateConcert

interface ConcertRepository {
    fun getById(concertId: String): Concert

    fun save(createConcert: CreateConcert): Concert

    fun getAll(): List<Concert>

    fun getByIds(concertIds: Set<String>): List<Concert>
}