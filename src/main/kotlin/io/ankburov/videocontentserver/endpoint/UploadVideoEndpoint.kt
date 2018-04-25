package io.ankburov.videocontentserver.endpoint

import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/upload")
class UploadVideoEndpoint {

    @PostMapping
    fun uploadVideo(@RequestPart("file") file: FilePart): String {
//        FilenameUtils.getBaseName()

//        Files.wri

        file.content()
                .map(DataBuffer::asByteBuffer)
                .map { it. }

        return "daf"
    }
}