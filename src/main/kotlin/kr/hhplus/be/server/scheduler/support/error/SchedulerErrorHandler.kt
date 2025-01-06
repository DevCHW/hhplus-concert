package kr.hhplus.be.server.scheduler.support.error

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ErrorHandler

class SchedulerErrorHandler : ErrorHandler {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun handleError(throwable: Throwable) {
        val e = throwable as Exception
        log.error("Scheduler error occurred. message = {}", e.message, e)
    }
}