package io.ankburov.videocontentserver.endpoint

import io.ankburov.videocontentserver.service.VideoContentStorage
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@RequestMapping("/dash-storage")
class MpegDashContentStorageEndpoint(
        val contentStorage: VideoContentStorage
) {

    @GetMapping("/{folder}/{fileName}", produces = ["application/dash+xml"])
    fun getMpd(@PathVariable("folder") folder: String,
               @PathVariable("fileName") fileName: String): Mono<out Resource> {
        return Mono.just(folder to fileName)
                .map { (folderArg, fileNameArg) -> contentStorage.getFile(folderArg, fileNameArg) }
                .subscribeOn(Schedulers.elastic())
    }

    @GetMapping("/{folder}/{representationId}/{fileName}", produces = ["text/plain"])
    fun getMp4Chunk(@PathVariable("folder") folder: String,
                    @PathVariable("representationId") representationId: String,
                    @PathVariable("fileName") fileName: String): Mono<out Resource> {
        return Mono.just(Triple(folder, representationId, fileName))
                .map { (folderArg, representationIdArg, fileNameArg) -> contentStorage.getFile(folderArg, representationIdArg, fileNameArg) }
                .subscribeOn(Schedulers.elastic())
    }
}