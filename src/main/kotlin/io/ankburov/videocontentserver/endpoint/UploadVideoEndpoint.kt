package io.ankburov.videocontentserver.endpoint

import io.ankburov.videocontentserver.model.MpegDto
import io.ankburov.videocontentserver.service.VideoContentStorage
import io.ankburov.videocontentserver.service.VideoEncoderService
import io.ankburov.videocontentserver.utils.writeToTempFile
import org.springframework.cache.Cache
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@RequestMapping("/upload")
class UploadVideoEndpoint(
        val videoEncoderService: VideoEncoderService,
        val contentStorage: VideoContentStorage,
        val fileCache: Cache
) {

    @PostMapping
    fun uploadVideo(@RequestPart("file") file: FilePart): Mono<MpegDto> {

        // example of ignite caching, caching based on file name is not really good idea
        val mpegDto = fileCache.get(file.filename(), MpegDto::class.java)
        if (mpegDto != null) {
            return Mono.just(mpegDto)
        }
        return file.content()
                .writeToTempFile()
                .flatMap(videoEncoderService::encodeToMpegDash)
                .map(contentStorage::saveMpegDashFiles)
                .doOnNext { savedMpegDto -> fileCache.put(file.filename(), savedMpegDto) }
                .subscribeOn(Schedulers.elastic())
    }
}