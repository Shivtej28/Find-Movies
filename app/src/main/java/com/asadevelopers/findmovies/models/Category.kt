package com.asadevelopers.findmovies.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(val id: Int = 0, val name: String) : Parcelable
