package androidx.media3.common

/**
 * 影像色彩資訊，對齊 androidx.media3.common.ColorInfo。
 */
class ColorInfo(
    val colorSpace: Int,
    val colorRange: Int,
    val colorTransfer: Int,
    val hdrStaticInfo: ByteArray? = null
) {
    companion object {
        const val COLOR_SPACE_BT709 = 1
        const val COLOR_SPACE_BT2020 = 6
        const val COLOR_RANGE_LIMITED = 2
        const val COLOR_RANGE_FULL = 1
        const val COLOR_TRANSFER_SDR = 3
        const val COLOR_TRANSFER_ST2084 = 6
        const val COLOR_TRANSFER_HLG = 7

        val SDR_BT709 = ColorInfo(COLOR_SPACE_BT709, COLOR_RANGE_LIMITED, COLOR_TRANSFER_SDR)
    }
}
