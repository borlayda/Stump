package hu.nullpointerexception.stump.config

import com.mongodb.Mongo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Author: Márton Tóth
 */
@Configuration
class PersistencyConfig {

    @Bean
    Mongo mongo() {
        new Mongo('localhost')
    }

}
