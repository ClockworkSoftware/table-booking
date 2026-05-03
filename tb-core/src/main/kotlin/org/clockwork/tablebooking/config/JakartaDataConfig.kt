package org.clockwork.tablebooking.config

import jakarta.persistence.EntityManagerFactory
import org.clockwork.tablebooking.repository.UserRepository
import org.clockwork.tablebooking.repository.UserRepository_
import org.hibernate.SessionFactory
import org.hibernate.StatelessSession
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JakartaDataConfig {
    @Bean
    fun statelessSession(emf: EntityManagerFactory): StatelessSession? {
        return emf.unwrap<SessionFactory?>(SessionFactory::class.java)!!.openStatelessSession()
    }

//    @Bean
//    fun userRepo(session: StatelessSession): UserRepository {
//        return UserRepository_(ObjectProvider session as ObjectProvider<StatelessSession>)
//    }

}