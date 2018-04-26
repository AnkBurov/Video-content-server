package io.ankburov.videocontentserver.integration

import io.ankburov.videocontentserver.model.MpegDto
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringRunner
import ru.rgs.k6.extension.ok
import ru.rgs.k6.extension.bodyNotNull
import ru.rgs.k6.extension.generateBody


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadVideoEndpointTest {

    private val expectedFile = ClassPathResource("samples/star_trails.mp4")

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun uploadVideo() {
        val (folderName, mpdFile) = restTemplate.postForEntity("/upload", generateBody(expectedFile), MpegDto::class.java)
                .ok()
                .bodyNotNull()
        assertNotNull(folderName)
        assertNotNull(mpdFile)
    }
}