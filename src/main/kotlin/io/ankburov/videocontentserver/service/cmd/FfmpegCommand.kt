package io.ankburov.videocontentserver.service.cmd

import io.ankburov.videocontentserver.model.QualityEnum
import io.ankburov.videocontentserver.utils.execute
import io.ankburov.videocontentserver.utils.getErrorMessage
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths

class FfmpegCommand {

    fun convert(file: Path, quality: QualityEnum): Pair<QualityEnum, Path> {
        val origFilePath = file.toAbsolutePath().toString()

        file.parent.toAbsolutePath().toString()
        val newFile = Paths.get(file.parent.toAbsolutePath().toString(), "${quality.code}.mp4")
        val newFilePath = newFile.toAbsolutePath().toString()

        val exec = "ffmpeg -i $origFilePath -s ${quality.code} -c:v libx264 -crf 23 -strict -2 $newFilePath".execute()
        exec.waitFor()

        return when (exec.exitValue()) {
            0  -> quality to newFile
            else -> throw IOException(exec.getErrorMessage())
        }
    }
}