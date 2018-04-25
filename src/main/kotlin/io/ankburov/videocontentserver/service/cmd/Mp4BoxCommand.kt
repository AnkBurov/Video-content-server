package io.ankburov.videocontentserver.service.cmd

import io.ankburov.videocontentserver.model.QualityEnum
import io.ankburov.videocontentserver.utils.execute
import io.ankburov.videocontentserver.utils.getErrorMessage
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

@Component
class Mp4BoxCommand {

    fun execute(convertedFiles: List<Pair<QualityEnum, Path>>): Path {
        require(convertedFiles.isNotEmpty()) { "converted mp4 file list cannot be empty" }

        val mpegDashDir = Files.createTempDirectory("split-mp4-to-mpeg-dash-fragments")
        val mpegDashDirPath = mpegDashDir.toAbsolutePath().toString()

        val representations = convertedFiles.map { (quality, file) -> "${file.toAbsolutePath()}#video:id=${quality.code}" }
                .joinToString(separator = " ")
                .plus(" ")
                .plus("${convertedFiles[0].second.toAbsolutePath()}#audio:id=audio")

        val exec = ("MP4Box -dash 8000 -frag 8000 -rap " +
                "-segment-name '\$RepresentationID\$/segment_' " +
                "-out $mpegDashDirPath/mpeg-dash.mpd " +
                representations).execute()
        exec.waitFor()

        return when (exec.exitValue()) {
            0  -> mpegDashDir
            else -> throw IOException(exec.getErrorMessage())
        }
    }


    //MP4Box -dash 8000 -frag 8000 -rap -segment-name '$RepresentationID$/segment_' with_sound_1080p.mp4#video:id=1080p with_sound_720p.mp4#video:id=720p with_sound_1080p.mp4#audio:id=audio
}