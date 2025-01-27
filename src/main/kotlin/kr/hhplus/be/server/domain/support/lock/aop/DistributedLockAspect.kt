package kr.hhplus.be.server.domain.support.lock.aop

import kr.hhplus.be.server.domain.support.lock.LockResource
import kr.hhplus.be.server.domain.support.lock.LockTemplate
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
    private val lockTemplate: LockTemplate,
) {

    @Around("@annotation(kr.hhplus.be.server.domain.support.lock.aop.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method: Method = signature.method
        val lockAnnotation = method.getAnnotation(DistributedLock::class.java)
        val lockName = getLockName(
            signature.parameterNames,
            joinPoint.args,
            lockAnnotation.key,
            lockAnnotation.resource,
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

    private fun getLockName(parameterNames: Array<String>, args: Array<Any?>, key: String, prefix: LockResource): String {
        val lockPrefix = "LOCK:${prefix.name.lowercase()}:"
        val parser: ExpressionParser = SpelExpressionParser()
        val context = StandardEvaluationContext()

        for (i in parameterNames.indices) {
            context.setVariable(parameterNames[i], args[i])
        }
        return lockPrefix + parser.parseExpression(key).getValue(context, Any::class.java)
    }
}