package com.sword.health.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class BreedGroup : Parcelable {
    Herding,
    Hound,
    NonSporting,
    Sporting,
    Terrier,
    Toy,
    Working,
    Unknown
}