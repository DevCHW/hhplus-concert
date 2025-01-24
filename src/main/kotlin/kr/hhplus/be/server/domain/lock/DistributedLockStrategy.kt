package kr.hhplus.be.server.domain.lock

enum class DistributedLockStrategy(
    val clientName: String,
) {
    REDIS_PUB_SUB("redissonLockClient"),
    REDIS_SPIN_LOCK("lettuceLockClient"),
    MYSQL_NAMED_LOCK("namedLockClient"),
}