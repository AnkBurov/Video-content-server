package io.ankburov.videocontentserver.endpoint

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@RequestMapping("/dash")
class MpegDashVideoEndpoint {

    @GetMapping("/{fileName}", produces = ["application/dash+xml"])
    fun getMp4(@PathVariable("fileName") fileName: String): Mono<out Resource> {
        return Mono.just(fileName)
                .map { ClassPathResource("samples/$it") }
                .filter(ClassPathResource::exists)
                .subscribeOn(Schedulers.elastic())
    }

    @GetMapping("/{representationId}/{fileName}", produces = ["text/plain"])
    fun getMp4Chunk(@PathVariable representationId: String, @PathVariable("fileName") fileName: String): Mono<out Resource> {
        return Mono.just(representationId to fileName)
                .map { ClassPathResource("samples/${it.first}/${it.second}") }
                .filter(ClassPathResource::exists)
                .subscribeOn(Schedulers.elastic())
    }
}