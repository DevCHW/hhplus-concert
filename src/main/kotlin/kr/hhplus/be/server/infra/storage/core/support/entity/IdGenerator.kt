package kr.hhplus.be.server.infra.storage.core.support.entity

import com.github.f4b6a3.tsid.TsidCreator

object IdGenerator {
    fun generate(): String = TsidCreator.getTsid().toString()
}