package com.sword.health.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image (
    val id: String = "",
    val width: Int = -1,
    val height: Int = -1,
    val url: String = "",
) : Parcelable {

    override fun toString(): String {
        return "Image(id='$id', width=$width, height=$height, url='$url')"
    }
}
