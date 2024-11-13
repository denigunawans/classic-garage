package com.dng.classicgarage.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val brand: String,
    val photo: String,
    val engine: String,
    val production: String,
    val country: String,
    val dimension: String,
    val description: String
) : Parcelable
