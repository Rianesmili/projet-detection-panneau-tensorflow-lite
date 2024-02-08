package fr.mastersime.panshare.model

import android.graphics.Bitmap

data class PhotoData(
    val image: Bitmap?,
    val location: Location?,
    var type: String? = "Unknown"
)

data class Location(
    val longitude: Double?,
    val latitude: Double?
)