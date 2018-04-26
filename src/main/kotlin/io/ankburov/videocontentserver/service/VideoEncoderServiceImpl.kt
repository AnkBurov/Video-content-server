package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.model.QualityEnum
import io.ankburov.videocontentserver.service.cmd.FfmpegCommand
import io.ankburov.videocontentserver.service.cmd.Mp4BoxCommand
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.nio.file.Path

@Service
class VideoEncoderServiceImpl(
        val mp4Converter: FfmpegCommand,
        val mpegDashConverter: Mp4BoxCommand
) : VideoEncoderService {


    override fun encodeToMpegDash(origFile: Path): Path {
        val convertedMp4Files = Flux.fromIterable(QualityEnum.values().asIterable())
                .parallel()
                .runOn(Schedulers.parallel())
                .map { quality -> mp4Converter.convert(origFile, quality) }
                .sequential()
                .collectList()
                .block() ?: emptyList()

        // folder with mpeg dash content
        return mpegDashConverter.execute(convertedMp4Files)

//        return contentStorage.saveMpegDashFiles(mpegDashDir)
    }
}