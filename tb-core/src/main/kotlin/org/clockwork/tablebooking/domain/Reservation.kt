package org.clockwork.tablebooking.domain

import jakarta.persistence.*
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
    val client: User,
    @ManyToOne
    @JoinColumn(name = "place_id")
    val place: Place
)