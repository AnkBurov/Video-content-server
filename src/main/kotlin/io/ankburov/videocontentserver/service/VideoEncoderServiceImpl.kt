package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.model.QualityEnum
import io.ankburov.videocontentserver.service.cmd.FfmpegCommand
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class VideoEncoderServiceImpl(
        val mp4Converter: FfmpegCommand
) : VideoEncoderService {


    override fun encodeToMpegDash(byteBuffer: ByteBuffer): Path {
        byteBuffer.rewind()
        require(byteBuffer.hasRemaining()) { "uploading file cannot be empty" }

        val mp4TempDir = Files.createTempDirectory("encode-to-mp4")

        val origMp4 = Paths.get(mp4TempDir.toAbsolutePath().toString(), "orig.mp4")
        Files.newByteChannel(origMp4, StandardOpenOption.CREATE)
                .write(byteBuffer)

        val convertedMp4Files = Flux.fromIterable(QualityEnum.values().asIterable())
                .parallel()
                .runOn(Schedulers.parallel())
                .map { quality -> mp4Converter.convert(origMp4, quality) }
                .sequential()
                .collectList()
                .block() ?: emptyList()

        convertedMp4Files.

    }
}