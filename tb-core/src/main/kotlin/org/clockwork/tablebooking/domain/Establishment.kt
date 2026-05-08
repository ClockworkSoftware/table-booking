package org.clockwork.tablebooking.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Establishment(
    val address: String,
    @OneToMany(mappedBy = "establishment")
    val places: List<Place> = listOf()
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}
