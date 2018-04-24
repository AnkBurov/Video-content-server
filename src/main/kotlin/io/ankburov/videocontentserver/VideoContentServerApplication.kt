package io.ankburov.videocontentserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class VideoContentServerApplication {

    private val indexHtml = ClassPathResource("static/index.html")

    @Bean
    fun mainRouter() = router {
        GET("/") {
            ok().contentType(TEXT_HTML).syncBody(indexHtml)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<VideoContentServerApplication>(*args)
}
