package org.clockwork.tablebooking.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Place(
    @Id
    @GeneratedValue
    val id: Long,

    @Column(unique = true)
    var labelNumber: Int,
    var reservationPrice: Double,
    var capacity: Int
)
