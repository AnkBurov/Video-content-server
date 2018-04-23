package io.ankburov.videocontentserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VideoContentServerApplication

fun main(args: Array<String>) {
    runApplication<VideoContentServerApplication>(*args)
}
