package org.clockwork.tablebooking.domain

import jakarta.persistence.*
import org.clockwork.tablebooking.dto.establishment.EstablishmentView

@Entity
class Establishment(
    @Column(unique = true)
    val address: String,
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User,

    @OneToMany(mappedBy = "establishment")
    val places: List<Place> = listOf()
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    fun toDto(): EstablishmentView {
        return EstablishmentView(
            id!!,
            address,
            owner.toDto()
        )
    }
}
