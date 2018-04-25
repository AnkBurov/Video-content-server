package io.ankburov.videocontentserver.utils

fun String.execute(): Process {
    return Runtime.getRuntime().exec(this)
}

fun Process.getErrorMessage(): String {
    return this.errorStream
            .bufferedReader()
            .readLines()
            .last()
}