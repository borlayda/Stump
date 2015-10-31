package hu.nullpointerexception.stump.config

import hu.nullpointerexception.stump.security.StumpUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
/**
 * Author: Márton Tóth
 */
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        http.httpBasic()
        http.authorizeRequests().antMatchers("/api/user/register").anonymous().and()
                .authorizeRequests().anyRequest().authenticated()
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        new BCryptPasswordEncoder(4)
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new StumpUserDetailsService()
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
    }


}
