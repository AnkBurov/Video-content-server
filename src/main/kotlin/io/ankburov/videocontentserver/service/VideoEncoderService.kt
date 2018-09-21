package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.model.MpegDashDir
import reactor.core.publisher.Mono
import java.nio.file.Path

interface VideoEncoderService {

    fun encodeToMpegDash(origFile: Path): Mono<MpegDashDir>
}