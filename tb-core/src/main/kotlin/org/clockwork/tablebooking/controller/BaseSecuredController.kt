package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.util.UserContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
abstract class BaseSecuredController {
    @Autowired
    lateinit var userContext: UserContext
}