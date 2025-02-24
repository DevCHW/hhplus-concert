package kr.hhplus.be.server.infra.storage.core.support.entity

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
abstract class BaseEntity(
    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

) : Persistable<String> {
    @Id
    private val id: String = TsidCreator.getTsid().toString()

    @Transient
    private var _isNew = true

    override fun getId(): String = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    override fun hashCode() = Objects.hashCode(id)

    private fun getIdentifier(obj: Any): Any? {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as BaseEntity).id
        }
    }

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }

}