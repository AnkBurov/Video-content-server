package io.ankburov.videocontentserver.utils

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecuteResultHandler
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.PumpStreamHandler
import org.springframework.core.io.buffer.DataBuffer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.stream.Collectors

fun String.execute(): Process {
    return Runtime.getRuntime().exec(this)
}

fun Path.absolutePath() = this.toAbsolutePath().toString()

fun Flux<DataBuffer>.writeToTempFile(): Mono<Path> {
    val tempDirectory = Files.createTempDirectory("upload_orig")
    val tempFile = Files.createTempFile(tempDirectory, "orig", "")
    Files.newByteChannel(tempFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND).use { byteChannel ->
        this.map(DataBuffer::asByteBuffer)
                .map(byteChannel::write)
                .subscribe()
    }
    return tempFile.toMono()
}

fun CommandLine.execute(stdOut: ByteArrayOutputStream, stdErr: ByteArrayOutputStream): Int {
    val defaultExecutor = DefaultExecutor()
    val psh = PumpStreamHandler(stdOut, stdErr)
    defaultExecutor.streamHandler = psh

    val resultHandler = DefaultExecuteResultHandler()

    defaultExecutor.execute(this, resultHandler)

    resultHandler.waitFor()
    return resultHandler.exitValue
}

//todo maybe rewrite without stdout and stderr holding in memory
inline fun <T> usingByteOutputStreams(lambda: (ByteArrayOutputStream, ByteArrayOutputStream) -> T): T {
    ByteArrayOutputStream().use { stdOut ->
        ByteArrayOutputStream().use { stdErr ->
            return lambda.invoke(stdOut, stdErr)
        }
    }
}


fun getErrorCause(stdErr: ByteArrayOutputStream): String {
    return ByteArrayInputStream(stdErr.toByteArray())
            .bufferedReader()
            .lines()
            .collect(Collectors.toList())
            .last()
}