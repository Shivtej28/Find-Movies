package com.asadevelopers.findmovies.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int = 0,
    var movietitle: String? = "",
    var movieImage: String? = "",
    var runtime: String? = "",
    var storyLine : String? = "",
    var releasedDate: String? = "",
    var rating: String? = "",
    var language: String? = "",
    var liked: Boolean = false
) : Parcelable

