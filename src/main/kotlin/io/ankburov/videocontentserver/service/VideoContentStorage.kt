package io.ankburov.videocontentserver.service

import java.nio.file.Path

interface VideoContentStorage {

    /**
     * @return folder name with content
     */
    fun saveMpegDashFiles(dir: Path): String
}