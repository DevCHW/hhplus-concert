package kr.hhplus.be.server.domain.support.component.lock

class LockedNamesHolder {
    companion object {
        private val lockedNamesThreadLocal: ThreadLocal<MutableList<String>> = ThreadLocal.withInitial { mutableListOf() }

        fun get(): List<String> {
            return lockedNamesThreadLocal.get().toList()
        }

        fun add(lockName: String) {
            lockedNamesThreadLocal.get().add(lockName)
        }

        fun remove(lockName: String) {
            val lockedNames = lockedNamesThreadLocal.get()
            lockedNames.remove(lockName)

            if (lockedNames.isEmpty()) {
                lockedNamesThreadLocal.remove()
            }
        }
    }
}