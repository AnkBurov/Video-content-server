package io.ankburov.videocontentserver.service.cmd

import io.ankburov.videocontentserver.model.QualityEnum
import io.ankburov.videocontentserver.utils.absolutePath
import io.ankburov.videocontentserver.utils.execute
import io.ankburov.videocontentserver.utils.getErrorCause
import io.ankburov.videocontentserver.utils.usingByteOutputStreams
import org.apache.commons.exec.CommandLine
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
class FfmpegCommand {

    fun convert(file: Path, quality: QualityEnum): Pair<QualityEnum, Path> {
        require(file.toFile().exists()) { "Converting file should exist" }
        val origFilePath = file.absolutePath()

        val newFile = Paths.get(file.parent.absolutePath(), "${UUID.randomUUID()}_${quality.code}.mp4")
        val newFilePath = newFile.absolutePath()

        val cmdLine = CommandLine("ffmpeg")
        cmdLine.addArgument("-i")
        cmdLine.addArgument(origFilePath, true)
        cmdLine.addArgument("-s")
        cmdLine.addArgument(quality.code, true)
        cmdLine.addArgument("-c:v")
        cmdLine.addArgument("libx264")
        cmdLine.addArgument("-crf")
        cmdLine.addArgument("23")
        cmdLine.addArgument("-strict")
        cmdLine.addArgument("-2")
        cmdLine.addArgument(newFilePath, true)


        usingByteOutputStreams { stdOut, stdErr ->
            val exitValue = cmdLine.execute(stdOut, stdErr)

            return when (exitValue) {
                0 -> quality to newFile
                else -> throw IOException(getErrorCause(stdErr))
            }
        }
    }
}