package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.utils.absolutePath
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@Service
class VideoContentStorageImpl : VideoContentStorage {

    private val videoContentDir = Files.createTempDirectory("video-content-storage")

    override fun saveMpegDashFiles(dir: Path): Path {
        val uuid = UUID.randomUUID().toString()
        val newDir = File(videoContentDir.absolutePath(), uuid)
        FileUtils.copyDirectory(dir.toFile(), newDir)
        return newDir.toPath()
    }
}