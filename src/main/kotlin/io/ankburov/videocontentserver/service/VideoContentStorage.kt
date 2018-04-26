package io.ankburov.videocontentserver.service

import io.ankburov.videocontentserver.model.MpegDashDir
import io.ankburov.videocontentserver.model.MpegDto
import org.springframework.core.io.Resource

interface VideoContentStorage {

    /**
     * @return folder name with content
     */
    fun saveMpegDashFiles(mpegDashDir: MpegDashDir): MpegDto

    fun getFile(folder: String, fileName: String): Resource

    fun getFile(folder: String, representation: String, fileName: String): Resource
}