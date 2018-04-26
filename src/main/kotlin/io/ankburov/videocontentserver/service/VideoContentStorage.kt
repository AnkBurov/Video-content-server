package io.ankburov.videocontentserver.service

import org.springframework.core.io.Resource
import java.nio.file.Path

interface VideoContentStorage {

    /**
     * @return folder name with content
     */
    fun saveMpegDashFiles(dir: Path): String

    fun getFile(folder: String, fileName: String): Resource

    fun getFile(folder: String, representation: String, fileName: String): Resource
}