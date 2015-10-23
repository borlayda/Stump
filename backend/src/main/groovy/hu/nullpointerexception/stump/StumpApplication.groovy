package hu.nullpointerexception.stump

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class StumpApplication {

    static def main(String[] args) {
        def ctx = SpringApplication.run(StumpApplication.class, args)
    }

}
