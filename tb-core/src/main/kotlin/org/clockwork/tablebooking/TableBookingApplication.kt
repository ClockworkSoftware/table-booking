package org.clockwork.tablebooking

import org.clockwork.tablebooking.config.JwtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(JwtConfig::class)
//@SpringBootApplication(exclude = [ ErrorMvcAutoConfiguration::class ] )
@SpringBootApplication
class TableBookingApplication

fun main(args: Array<String>) {
	runApplication<TableBookingApplication>(*args)
}
