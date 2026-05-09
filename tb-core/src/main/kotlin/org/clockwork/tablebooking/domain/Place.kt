package org.clockwork.tablebooking.domain

import jakarta.persistence.*
import org.clockwork.tablebooking.dto.place.PlaceView
import java.time.Instant

@Entity
data class Place(
    @Column(unique = true)
    var labelNumber: Int,
    var reservationPrice: Double,
    var guestCapacity: Int,

    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = false)
    val establishment: Establishment,

    @OneToMany(orphanRemoval = true, mappedBy = "place")
    val reservations: List<Reservation> = listOf()
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    fun toDto(): PlaceView {
        return PlaceView(
            id!!,
            labelNumber,
            reservationPrice,
            guestCapacity
        )
    }

    fun isFreeBetween(start: Instant, end: Instant): Boolean {
        return reservations.none { it.intersects(start, end) }
    }
}
