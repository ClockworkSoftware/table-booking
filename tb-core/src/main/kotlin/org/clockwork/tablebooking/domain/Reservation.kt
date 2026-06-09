package org.clockwork.tablebooking.domain

import jakarta.persistence.*
import org.clockwork.tablebooking.dto.reservation.ReservationView
import java.time.Instant

@Entity
class Reservation (
    var start: Instant,
    var finish: Instant,
    var begun: Boolean,

    @ManyToOne
    @JoinColumn(name = "client_id")
    val client: User,
    @ManyToOne
    @JoinColumn(name = "place_id")
    val place: Place
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    fun intersects(otherStart: Instant, otherEnd: Instant): Boolean {
        val range = start..finish
        return otherStart in range || otherEnd in range
    }

    fun toDto(): ReservationView {
        return ReservationView(
            start,
            finish,
            begun,
            client.toDto(),
            place.toDto()
        )
    }

}