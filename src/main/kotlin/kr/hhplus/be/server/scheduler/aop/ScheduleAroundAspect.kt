package kr.hhplus.be.server.scheduler.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class ScheduleAroundAspect {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    fun executeScheduledAround(joinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        val className = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name

        return try {
            stopWatch.start()
            log.info("[Schedule] Task started - {}.{}", className, methodName)
            joinPoint.proceed()
        } catch (ex: Throwable) {
            log.error("[Schedule] Exception occurred in {}.{}: {}", className, methodName, ex.message, ex)
        } finally {
            stopWatch.stop()
            log.info("[Schedule] Task finished - {}.{} | Total Time: {}ms", className, methodName, stopWatch.totalTimeMillis)
        }
    }
}