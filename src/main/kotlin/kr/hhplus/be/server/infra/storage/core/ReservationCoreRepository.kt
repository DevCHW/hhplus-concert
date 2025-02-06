package kr.hhplus.be.server.infra.storage.core

import kr.hhplus.be.server.domain.reservation.ReservationRepository
import kr.hhplus.be.server.domain.reservation.model.CreateReservation
import kr.hhplus.be.server.domain.reservation.model.ModifyReservation
import kr.hhplus.be.server.domain.reservation.model.Reservation
import kr.hhplus.be.server.infra.storage.core.jpa.entity.ReservationEntity
import kr.hhplus.be.server.infra.storage.core.jpa.repository.ReservationEntityJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Repository
class ReservationCoreRepository(
    private val reservationJpaRepository: ReservationEntityJpaRepository,
) : ReservationRepository {

    override fun isExistBySeatId(seatId: String): Boolean {
        return reservationJpaRepository.existsBySeatId(seatId)
    }

    override fun save(createReservation: CreateReservation): Reservation {
        val reservationEntity = ReservationEntity.create(createReservation)
        return reservationJpaRepository.save(reservationEntity).toDomain()
    }

    override fun getByStatus(status: Reservation.Status): List<Reservation> {
        return reservationJpaRepository.findByStatus(status)
            .map { it.toDomain() }
    }

    override fun modifyStatusByIds(status: Reservation.Status, reservationIds: List<String>): Int {
        return reservationJpaRepository.updateStatusByIds(status, reservationIds)
    }

    @Transactional
    override fun modify(modifyReservation: ModifyReservation): Reservation {
        val reservationEntity = reservationJpaRepository.findByIdOrThrow(modifyReservation.id)
        return reservationEntity.modify(modifyReservation).toDomain()
    }

    override fun getById(reservationId: String): Reservation {
        return reservationJpaRepository.findByIdOrThrow(reservationId).toDomain()
    }

    override fun getBySeatIds(seatIds: List<String>): List<Reservation> {
        return reservationJpaRepository.findBySeatIdIn(seatIds)
            .map { it.toDomain() }
    }

    override fun getByIdWithLock(reservationId: String): Reservation {
        return reservationJpaRepository.findForUpdateById(reservationId)
    }

    override fun getByDateAndStatus(date: LocalDate, status: Reservation.Status): List<Reservation> {
        val reservationEntities =
            reservationJpaRepository.findByStatusAndCreatedAtBetween(
                status = status,
                start = date.atStartOfDay(),
                end = date.atTime(LocalTime.MAX).withNano(999999000),
            )
        return reservationEntities.map { it.toDomain() }
    }
}