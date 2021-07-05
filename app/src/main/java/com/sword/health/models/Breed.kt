package com.sword.health.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(
    @SerializedName("bred_for")
    val bredFor: String = "",

    @SerializedName("breed_group")
    val breedGroup: String = "",

    @SerializedName("reference_image_id")
    val referenceImageID: String = "",

    @SerializedName("life_span")
    val lifeSpan: String = "",

    val height: Eight = Eight("", ""),
    val id: Int = -1,
    val image: Image = Image(),
    val name: String = "",
    val origin: String = "",
    val temperament: String = "",
    val weight: Eight = Eight(),
    val countryCode: String = "",
    val history: String = ""

) : Parcelable {

    override fun toString(): String {
        return "Breed(bredFor='$bredFor', breedGroup=$breedGroup, referenceImageID='$referenceImageID', " +
                "lifeSpan='$lifeSpan', height=$height, id=$id, image=$image, name='$name', origin='$origin', " +
                "temperament='$temperament', weight=$weight, countryCode='$countryCode', history='$history')"
    }
}
