package io.ankburov.videocontentserver.model

import java.nio.file.Path

data class MpegDashDir(
        val mpdName: String,
        val filesDir: Path
)