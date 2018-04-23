package io.ankburov.videocontentserver.integration

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringRunner
import ru.rgs.k6.extension.bodyNotNull
import ru.rgs.k6.extension.equalsTo
import ru.rgs.k6.extension.isMp4ContentType
import ru.rgs.k6.extension.ok

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DirectContentVideoEndpointTest {

    private val expectedFile = ClassPathResource("samples/$FILE_NAME")

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun getMp4() {
        restTemplate.getForEntity("/direct/$FILE_NAME", ByteArray::class.java)
                .ok()
                .isMp4ContentType()
                .bodyNotNull()
                .size equalsTo expectedFile.file.length().toInt()
    }

    companion object {
        val FILE_NAME = "star_trails.mp4"
    }
}