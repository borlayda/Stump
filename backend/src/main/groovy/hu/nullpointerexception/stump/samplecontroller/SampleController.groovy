package hu.nullpointerexception.stump.samplecontroller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by totlol on 23/10/15.
 */
@RestController('sample')
@RequestMapping('/')
class SampleController {

    @RequestMapping
    def root() {
        'Yay, it works!'
    }

}