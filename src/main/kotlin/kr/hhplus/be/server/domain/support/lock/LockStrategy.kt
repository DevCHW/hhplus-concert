package kr.hhplus.be.server.domain.support.lock

enum class LockStrategy(
    val clientName: String,
) {
    REDIS_PUB_SUB("redissonLockClient"),
    REDIS_SPIN_LOCK("lettuceLockClient"),
    MYSQL_NAMED_LOCK("namedLockClient"),
}