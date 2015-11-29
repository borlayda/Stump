package hu.nullpointerexception.stump

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


@SpringBootApplication
@EnableGlobalMethodSecurity
@EnableWebSecurity
class StumpApplication {

    static void main(String[] args) {
        def ctx = SpringApplication.run(StumpApplication.class, args)
    }

}
