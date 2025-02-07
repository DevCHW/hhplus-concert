package kr.hhplus.be.server.support

import com.github.f4b6a3.tsid.TsidCreator

class IdGenerator {
    companion object {
        fun generate(): String {
            return TsidCreator.getTsid().toString()
        }
    }
}