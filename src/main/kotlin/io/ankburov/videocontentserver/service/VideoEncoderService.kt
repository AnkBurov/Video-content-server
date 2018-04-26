package io.ankburov.videocontentserver.service

import java.nio.file.Path

interface VideoEncoderService {

    fun encodeToMpegDash(origFile: Path): Path
}