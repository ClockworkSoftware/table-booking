package org.clockwork.tablebooking

import org.clockwork.tablebooking.config.JwtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.webmvc.autoconfigure.error.ErrorMvcAutoConfiguration
import kotlin.reflect.KClass

@EnableConfigurationProperties(JwtConfig::class)
//@SpringBootApplication(exclude = [ ErrorMvcAutoConfiguration::class ] )
@SpringBootApplication
class TableBookingApplication

fun main(args: Array<String>) {
	runApplication<TableBookingApplication>(*args)
}
