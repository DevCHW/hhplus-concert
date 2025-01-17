package kr.hhplus.be.server.infra.storage.core.support

import kr.hhplus.be.server.domain.concert.model.CreateConcert
import kr.hhplus.be.server.domain.concert.model.CreateConcertSchedule
import kr.hhplus.be.server.domain.concert.model.CreateSeat
import kr.hhplus.be.server.infra.storage.core.jpa.entity.ConcertEntity
import kr.hhplus.be.server.infra.storage.core.jpa.entity.ConcertScheduleEntity
import kr.hhplus.be.server.infra.storage.core.jpa.entity.SeatEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.ConcertEntityJpaRepository
import kr.hhplus.be.server.infra.storage.core.jpa.repository.ConcertScheduleEntityJpaRepository
import kr.hhplus.be.server.infra.storage.core.jpa.repository.SeatEntityJpaRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
@Profile("local")
class LocalDBInit(
    private val concertEntityJpaRepository: ConcertEntityJpaRepository,
    private val concertScheduleEntityJpaRepository: ConcertScheduleEntityJpaRepository,
    private val seatEntityJpaRepository: SeatEntityJpaRepository,
) {

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun init() {
        // 콘서트 5개 생성
        val createConcerts = mutableListOf<CreateConcert>()
        for (i in 1..5) {
            createConcerts.add(
                CreateConcert(
                    title = concertTitles[i],
                    price = BigDecimal(100)
                )
            )
        }
        val concertEntities = concertEntityJpaRepository.saveAll(createConcerts.map { ConcertEntity.create(it) })

        // 콘서트별 콘서트 일정 5개 생성
        val createConcertSchedules = mutableListOf<CreateConcertSchedule>()
        for (concertEntity in concertEntities) {
            for (i in 1..5) {
                createConcertSchedules.add(
                    CreateConcertSchedule(
                        concertId = concertEntity.id,
                        location = locations[i],
                        concertAt = concertAts[i],
                    )
                )
            }
        }
        val concertScheduleEntities = concertScheduleEntityJpaRepository
            .saveAll(createConcertSchedules.map { ConcertScheduleEntity.create(it) })

        // 콘서트 일정 별 좌석 50개씩 생성
        val createSeats = mutableListOf<CreateSeat>()
        for (concertScheduleEntity in concertScheduleEntities) {
            for (i in 1..50) {
                createSeats.add(
                    CreateSeat(
                        concertScheduleId = concertScheduleEntity.id,
                        number = i,
                    )
                )
            }
        }
        val seatEntities = seatEntityJpaRepository.saveAll(createSeats.map { SeatEntity.create(it) })
    }

    companion object {
        private val concertTitles = arrayOf(
            "",
            "아이유 - Palette Live",
            "성시경 - 축가의 밤",
            "방탄소년단 - Beyond the Stage",
            "에스파 - Synk: Dive",
            "뉴진스 - Bunnies in Harmony",
        )

        private val locations = arrayOf(
            "",
            "올림픽공원 KSPO 돔 (체조경기장)",
            "고척 스카이돔",
            "서울월드컵경기장",
            "잠실실내체육관",
            "부산 벡스코",
        )
        private val now = LocalDateTime.now()

        private val concertAts = arrayOf(
            now,

            // 지난 일정 (현재 시간 기준 과거)
            now.minusDays(30).withHour(19).withMinute(0), // 30일 전 오후 7시
            now.minusDays(10).withHour(18).withMinute(30), // 10일 전 오후 6시 30분

            // 앞으로의 일정 (현재 시간 기준 미래)
            now.plusDays(10).withHour(18).withMinute(0), // 10일 후 오후 6시
            now.plusDays(20).withHour(19).withMinute(30), // 20일 후 오후 7시 30분
            now.plusDays(30).withHour(20).withMinute(0) // 30일 후 오후 8시
        )
    }
}