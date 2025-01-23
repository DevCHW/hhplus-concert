package kr.hhplus.be.server.api.support.lock

import kr.hhplus.be.server.domain.lock.LockTemplateService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class DistributedLockAspect(
    private val lockTemplate: LockTemplateService,
) {

    @Around("@annotation(kr.hhplus.be.server.api.support.lock.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method: Method = signature.method
        val lockAnnotation = method.getAnnotation(DistributedLock::class.java)
        val lockName = getLockName(
            signature.parameterNames,
            joinPoint.args,
            lockAnnotation.lockName,
        )

        return lockTemplate.withDistributedLock(
            lockName = lockName,
            waitTime = lockAnnotation.waitTime,
            releaseTime = lockAnnotation.releaseTime,
            strategy = lockAnnotation.strategy,
            timeUnit = lockAnnotation.timeUnit,
        ) {
            joinPoint.proceed()
        }
    }

    private fun getLockName(parameterNames: Array<String>, args: Array<Any?>, key: String): String {
        val lockPrefix = "LOCK:"
        val parser: ExpressionParser = SpelExpressionParser()
        val context = StandardEvaluationContext()

        for (i in parameterNames.indices) {
            context.setVariable(parameterNames[i], args[i])
        }
        return lockPrefix + parser.parseExpression(key).getValue(context, Any::class.java)
    }
}