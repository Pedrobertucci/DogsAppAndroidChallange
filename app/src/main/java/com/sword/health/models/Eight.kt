package com.sword.health.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Eight (
    val imperial: String = "",
    val metric: String = ""
) : Parcelable {

    override fun toString(): String {
        return "Eight(imperial='$imperial', metric='$metric')"
    }
}
