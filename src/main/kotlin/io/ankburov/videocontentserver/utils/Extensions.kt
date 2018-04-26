package io.ankburov.videocontentserver.utils

import org.springframework.core.io.buffer.DataBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

fun String.execute(): Process {
    return Runtime.getRuntime().exec(this)
}

fun Process.getErrorMessage(): String {
    return this.errorStream
            .bufferedReader()
            .readLines()
            .last()
}

fun Path.absolutePath() = this.toAbsolutePath().toString()

fun List<DataBuffer>.writeToTempFile(): Path {
    val tempFile = Files.createTempFile("orig", "")
    Files.newByteChannel(tempFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND).use { byteChannel ->
        this.forEach { byteChannel.write(it.asByteBuffer()) }
    }
    return tempFile
}