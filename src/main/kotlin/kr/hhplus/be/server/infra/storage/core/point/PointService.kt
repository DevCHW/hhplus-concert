package kr.hhplus.be.server.infra.storage.core.point

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author DevCHW
 * @since 2025-04-22
 */
@Service
class PointService(
    private val pointRepository: PointEntityJpaRepository,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun charge(userId: Long, amount: Int) {
        log.info("충전 시작")

        val point = pointRepository.findForUpdateByUserId(userId)

        log.info("충전에서 ${point.balance}원 조회됨, 버전: ${point.version}")

        point.balance += amount

        log.info("충전 종료")
    }

    fun use(userId: Long, amount: Int) {
        log.info("사용 시작")
        while (true) {
            try {
                val point = pointRepository.findById(userId).orElseThrow()
                log.info("사용에서 ${point.balance}원 조회됨, 버전: ${point.version}")
                Thread.sleep(3000) // 대기
                point.balance -= amount
                pointRepository.save(point)
            } catch (e: OptimisticLockingFailureException) {
                log.info("OptimisticLockingFailureException 발생. 재시도")
                Thread.sleep(500)
                continue
            }
            break
        }
        log.info("사용 종료")
    }
}