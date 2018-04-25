package io.ankburov.videocontentserver.utils

import java.nio.file.Path

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