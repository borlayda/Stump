package hu.nullpointerexception.stump.security

import hu.nullpointerexception.stump.model.User
import hu.nullpointerexception.stump.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

/**
 * Author: Márton Tóth
 */
@Service
class StumpUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository


    @PostConstruct
    def postConstruct() {
        if (userRepository.findByName("admin") == null) {
            def user = new User()
            user.name = "admin"
            user.password = "admin"
            userRepository.save(user)
        }
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        null
    }
}
