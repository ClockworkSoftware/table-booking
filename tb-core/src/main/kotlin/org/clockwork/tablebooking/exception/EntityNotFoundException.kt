package org.clockwork.tablebooking.exception

class EntityNotFoundException(
    entityName: String,
    idQuery: Long,
    additionalMessage: String? = null
) : NotFoundException(
    "Entity $entityName by id $idQuery not found!"
            + (additionalMessage?.let { "\nAdditional message: $it" } ?: "")
)