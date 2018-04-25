package io.ankburov.videocontentserver.integration

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.LinkedMultiValueMap
import ru.rgs.k6.extension.ok
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.util.MultiValueMap


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadVideoEndpointTest {

    private val expectedFile = ClassPathResource("samples/star_trails.mp4")

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun uploadVideo() {
        val path = restTemplate.postForEntity("/upload", generateBody(), String::class.java)
                .ok()
        println(path)
    }

    private fun generateBody(): MultiValueMap<String, HttpEntity<*>> {
        val builder = MultipartBodyBuilder()
        builder.part("file", expectedFile)
        return builder.build()
    }

}