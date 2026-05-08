package org.clockwork.tablebooking.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.clockwork.tablebooking.dto.place.CreatePlaceView
import org.clockwork.tablebooking.dto.place.PlaceView

@Entity
data class Place(
    @Column(unique = true)
    val labelNumber: Int,
    val reservationPrice: Double,
    val capacity: Int
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    fun toDto(): PlaceView {
        return PlaceView(
            id!!,
            labelNumber,
            reservationPrice,
            capacity
        )
    }

    companion object {
        fun fromDto(dto: CreatePlaceView): Place {
            dto.run {
                return Place(
                    labelNumber,
                    reservationPrice,
                    capacity
                )
            }
        }
    }
}
