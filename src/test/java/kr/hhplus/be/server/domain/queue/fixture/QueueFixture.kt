package kr.hhplus.be.server.domain.queue.fixture

import com.github.f4b6a3.tsid.TsidCreator
import kr.hhplus.be.server.domain.queue.model.QueueToken
import java.util.*

class QueueFixture {
    companion object {
        fun queueToken(
            userId: String = TsidCreator.getTsid().toString(),
            token: UUID = UUID.randomUUID(),
            status: QueueToken.Status = QueueToken.Status.ACTIVE,
        ): QueueToken {
            return QueueToken(
                userId = userId,
                token = token,
                status = status,
            )
        }
    }
}