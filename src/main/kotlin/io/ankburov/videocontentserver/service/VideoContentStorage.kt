package io.ankburov.videocontentserver.service

import java.nio.file.Path

interface VideoContentStorage {

    fun saveMpegDashFiles(dir: Path): Path
}