package io.ankburov.videocontentserver.endpoint

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import reactor.core.scheduler.Schedulers

/**
 * 'Streaming' (not the real streaming though, but reminds one thanks to MSE) of MP4 from direct file channel
 */
@RestController
@RequestMapping("/direct")
class DirectContentVideoEndpoint {

    private val mp4File = ClassPathResource("samples/star_trails.mp4")

    @GetMapping("/{fileName}", produces = ["video/mp4"])
    fun getMp4(@PathVariable("fileName") fileName: String): Mono<out Resource> {
        return mp4File.toMono()
                .subscribeOn(Schedulers.elastic())
    }
}