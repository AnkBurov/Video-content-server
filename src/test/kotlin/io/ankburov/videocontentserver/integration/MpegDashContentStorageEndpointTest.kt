package io.ankburov.videocontentserver.integration

import io.ankburov.videocontentserver.model.MpegDto
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringRunner
import ru.rgs.k6.extension.bodyNotNull
import ru.rgs.k6.extension.generateBody
import ru.rgs.k6.extension.isDashXml
import ru.rgs.k6.extension.ok

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MpegDashContentStorageEndpointTest {

    private val expectedFile = ClassPathResource("samples/with_sound.mp4")

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun convertMp4AndGetMpd() {
        val (folderName, mpdFile) = restTemplate.postForEntity("/upload", generateBody(expectedFile), MpegDto::class.java)
                .ok()
                .bodyNotNull()

        val url = "/dash-storage/$folderName/$mpdFile"
        val mpd = restTemplate.getForEntity(url, String::class.java)
                .ok()
                .isDashXml()
                .bodyNotNull()

        Assert.assertTrue(StringUtils.containsIgnoreCase(mpd, "MPD"))
    }
}