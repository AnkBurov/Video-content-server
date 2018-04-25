package io.ankburov.videocontentserver.endpoint

import io.ankburov.videocontentserver.service.VideoContentStorage
import io.ankburov.videocontentserver.service.VideoEncoderService
import io.ankburov.videocontentserver.utils.absolutePath
import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
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
                .map(DataBuffer::asByteBuffer)
                .toMono() //todo maybe several blocks above, think about
                .map(videoEncoderService::encodeToMpegDash)
                .map(contentStorage::saveMpegDashFiles)
                .map(Path::absolutePath)
                .subscribeOn(Schedulers.elastic())
    }
}