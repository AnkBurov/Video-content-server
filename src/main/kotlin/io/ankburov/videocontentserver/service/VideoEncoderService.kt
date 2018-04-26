package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.model.MpegDashDir
import java.nio.file.Path

interface VideoEncoderService {

    fun encodeToMpegDash(origFile: Path): MpegDashDir
}