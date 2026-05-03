package org.clockwork.tablebooking.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.Instant

@Entity
data class Reservation (
    @Id
    @GeneratedValue
    val id: Long,

    var start: Instant,
    var finish: Instant,
    var begun: Boolean = false,

    @ManyToOne
    val user: User,
    @ManyToOne
    val place: Place
)