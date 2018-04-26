package io.ankburov.videocontentserver.unit

import io.ankburov.videocontentserver.model.QualityEnum
import io.ankburov.videocontentserver.service.cmd.FfmpegCommand
import org.apache.commons.io.FileUtils
import org.assertj.core.util.Files
import org.junit.Assert.assertTrue
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import java.io.IOException

class FfmpegCommandTest {

    private val command = FfmpegCommand()

    private val expectedFile = ClassPathResource("samples/star_trails.mp4")

    @Test
    fun convert() {
        val quality = QualityEnum.HD_480
        val temporaryFile = Files.newTemporaryFile()
        FileUtils.copyFile(expectedFile.file, temporaryFile)

        val (_, convertedFile) = command.convert(temporaryFile.toPath(), quality)

        assertTrue(convertedFile.toFile().exists())
        assertTrue(convertedFile.toFile().name.contains(quality.code))
    }

    /*@Test(expected = IOException::class)
    fun convertError() {
        val quality = QualityEnum.HD_480
        val temporaryFile = Files.newTemporaryFile()
        temporaryFile.delete()

        command.convert(temporaryFile.toPath(), quality)
    }*/
}