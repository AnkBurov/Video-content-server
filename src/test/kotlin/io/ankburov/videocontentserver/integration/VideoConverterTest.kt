package io.ankburov.videocontentserver.integration

import org.apache.commons.io.IOUtils
import org.assertj.core.util.Files
import org.junit.Assert
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import java.io.File

class VideoConverterTest {

    private val expectedFile = ClassPathResource("samples/star_trails.mp4")


    @Test
    fun name() {
        val tempDir = Files.newTemporaryFolder()

        println(tempDir.absolutePath)

        val newFile = File(tempDir, "1080p.mp4")


        val exec = Runtime.getRuntime().exec("ffmpeg -i ${expectedFile.file.absolutePath} -s hd1080 -c:v libx264 -crf 23 -strict -2 ${newFile.absolutePath}")
        exec.waitFor()

//        IOUtils.toString(exec.getErrorStream())

        exec.errorStream.bufferedReader()
                .readLines()


        Assert.assertEquals(0, exec.exitValue())
    }

    @Test
    fun error() {
        val tempDir = Files.newTemporaryFolder()

        println(tempDir.absolutePath)

        val newFile = File(tempDir, "1080p.mp4")


        val exec = Runtime.getRuntime().exec("ffmpeg -i ${expectedFile.file.absolutePath} -s hd1080 -c:v lib64 -crf 23 -strict -2 ${newFile.absolutePath}")
        exec.waitFor()


        println(exec.errorStream.bufferedReader()
                .readLines()
                .last())


        Assert.assertEquals(1, exec.exitValue())
    }
}