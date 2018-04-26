package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.model.MpegDashDir
import io.ankburov.videocontentserver.model.MpegDto
import io.ankburov.videocontentserver.utils.absolutePath
import org.apache.commons.io.FileUtils
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class VideoContentStorageImpl : VideoContentStorage {

    private val videoContentDir = Files.createTempDirectory("video-content-storage")

    override fun saveMpegDashFiles(mpegDashDir: MpegDashDir): MpegDto {
        val uuid = UUID.randomUUID().toString()
        val newDir = File(videoContentDir.absolutePath(), uuid)
        FileUtils.copyDirectory(mpegDashDir.filesDir.toFile(), newDir)
        return MpegDto(uuid, mpegDashDir.mpdName)
    }

    override fun getFile(folder: String, fileName: String): Resource {
        return Optional.ofNullable(Paths.get(videoContentDir.absolutePath(), folder, fileName))
                .map(Path::toFile)
                .filter { it.exists() }
                .map(::FileSystemResource)
                .orElseThrow { IllegalArgumentException("file is not exist") }
    }

    override fun getFile(folder: String, representation: String, fileName: String): Resource {
        val file = Paths.get(videoContentDir.absolutePath(), folder, representation, fileName)
        require(file.toFile().exists()) { "file is not exist" }

        return FileSystemResource(file.toFile())
    }
}