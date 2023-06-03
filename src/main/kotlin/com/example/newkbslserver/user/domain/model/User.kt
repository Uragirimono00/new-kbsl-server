package com.example.newkbslserver.user.domain.model

import com.example.newkbslserver.boot.domain.model.BaseEntity
import com.example.newkbslserver.user.enums.ERole
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class User(
    username: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val seq: Long? = null

    @JsonIgnore
    private val password: String? = null

    private val username: String = username

    private val imageUrl: String? = null

    @Enumerated(EnumType.STRING)
    private val eRole: ERole? = null

    private val beatleaderId: String? = null
}