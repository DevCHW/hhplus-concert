package kr.hhplus.be.server.infra.storage.core.jpa

import kr.hhplus.be.server.domain.concert.model.Concert
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertJpaRepository : JpaRepository<Concert, String> {
}