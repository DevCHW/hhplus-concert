package kr.hhplus.be.server.infra.storage.core.jpa.entity.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.*

@Converter
class UUIDToStringConverter : AttributeConverter<UUID?, String?> {
    override fun convertToDatabaseColumn(attribute: UUID?): String? {
        return attribute?.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): UUID? {
        return if (dbData != null) UUID.fromString(dbData) else null
    }
}