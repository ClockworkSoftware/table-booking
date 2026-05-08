package org.clockwork.tablebooking.domain

import jakarta.persistence.*
import org.clockwork.tablebooking.dto.place.PlaceView

@Entity
data class Place(
    @Column(unique = true)
    val labelNumber: Int,
    val reservationPrice: Double,
    val guestCapacity: Int,

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
}
