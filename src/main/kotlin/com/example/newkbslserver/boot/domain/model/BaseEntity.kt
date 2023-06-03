package com.example.newkbslserver.boot.domain.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Getter
import lombok.Setter
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @CreatedDate
    private val createdDateTime: LocalDateTime? = null

    @LastModifiedDate
    private val modifiedDateTime: LocalDateTime? = null

    @ColumnDefault("1")
    private val status: Int? = null
}