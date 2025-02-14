package kr.hhplus.be.server.listner

import kr.hhplus.be.server.domain.client.DataPlatformService
import kr.hhplus.be.server.listner.event.ReservationCompleteEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationCompleteEventListener(
    private val dataPlatformService: DataPlatformService,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    fun listen(event: ReservationCompleteEvent) {
        dataPlatformService.sendData(event.toModel())
    }
}
