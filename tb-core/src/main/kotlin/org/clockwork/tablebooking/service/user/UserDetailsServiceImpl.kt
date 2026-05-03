//package org.clockwork.tablebooking.service.user
//
//import org.clockwork.tablebooking.repository.UserRepository
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Service
//
//@Service
//class UserDetailsServiceImpl (
//    val userRepo: UserRepository
//) : UserDetailsService {
//    override fun loadUserByUsername(username: String): UserDetails {
//        val user = userRepo.findByLogin(username)
//            ?: throw UsernameNotFoundException("User $username not found")
//        return User
//            .withUsername(user.login)
//            .password(user.password)
//            .authorities(*user.role.authorities.toTypedArray())
//            .build()
//    }
//}