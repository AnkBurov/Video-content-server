package io.ankburov.videocontentserver.endpoint

import io.ankburov.videocontentserver.service.VideoContentStorage
import io.ankburov.videocontentserver.service.VideoEncoderService
import io.ankburov.videocontentserver.utils.absolutePath
import io.ankburov.videocontentserver.utils.writeToTempFile
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.nio.file.Path

@RestController
@RequestMapping("/upload")
class UploadVideoEndpoint(
        val videoEncoderService: VideoEncoderService,
        val contentStorage: VideoContentStorage
) {

    @PostMapping
    fun uploadVideo(@RequestPart("file") file: FilePart): Mono<String> {
        // file.filename()

        return file.content()
                .collectList() //todo maybe write without converting to mono
                .map { buffers -> buffers.writeToTempFile() }
                .map(videoEncoderService::encodeToMpegDash)
                .map(contentStorage::saveMpegDashFiles)
                .map(Path::absolutePath)
                .subscribeOn(Schedulers.elastic())
    }
}