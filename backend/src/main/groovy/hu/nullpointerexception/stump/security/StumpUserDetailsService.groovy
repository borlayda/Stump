package hu.nullpointerexception.stump.security

import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.model.User
import hu.nullpointerexception.stump.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

import javax.annotation.PostConstruct
/**
 * Author: Márton Tóth
 */
class StumpUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    def postConstruct() {
        if (userRepository.findByName("admin") == null) {
            def user = new User()
            user.name = "admin"
            user.email = "admin@stump.com"
            user.password = passwordEncoder.encode("admin")
            user.role = Role.ADMIN
            userRepository.save(user)
        }
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            def user = userRepository.findByName(username)
        if (user == null) {
            throw new UsernameNotFoundException("User not found.")
        }
        new org.springframework.security.core.userdetails.User(
                user.name,
                user.password,
                [new SimpleGrantedAuthority(user.role.name())]
        )
    }
}
