package io.ankburov.videocontentserver.integration

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringRunner
import ru.rgs.k6.extension.asString
import ru.rgs.k6.extension.bodyNotNull
import ru.rgs.k6.extension.equalsTo
import ru.rgs.k6.extension.isTextPlain
import ru.rgs.k6.extension.ok

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MpegDashVideoEndpoint {

    private val expectedFile = ClassPathResource("samples/${FILE_NAME}")

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun getMpd() {
        restTemplate.getForEntity("/dash/${FILE_NAME}", ByteArray::class.java)
                .ok()
                .isTextPlain()
                .bodyNotNull()
                .asString() equalsTo expectedFile.file.readText()
    }

    companion object {
        val FILE_NAME = "vid_rep_1_dash.mpd"
    }
}