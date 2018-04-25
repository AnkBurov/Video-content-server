package io.ankburov.videocontentserver.service

import java.nio.ByteBuffer
import java.nio.file.Path

interface VideoEncoderService {

    fun encodeToMpegDash(byteBuffer: ByteBuffer): Path
}