package hu.nullpointerexception.stump.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

/**
 * Created by Márton Tóth
 */
class StumpPrincipal extends User {

    hu.nullpointerexception.stump.model.User user

    StumpPrincipal(hu.nullpointerexception.stump.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.name, user.password, authorities)
        this.user = user
    }

    StumpPrincipal(hu.nullpointerexception.stump.model.User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.name, user.password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
        this.user = user
    }
}
