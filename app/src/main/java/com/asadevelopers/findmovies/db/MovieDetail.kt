package com.asadevelopers.findmovies.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "liked_movie_table", indices = [Index(value = ["id"], unique = true)])
data class MovieDetail(

    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "movie_title")
    var movietitle: String? = "",
    @ColumnInfo(name = "movie_image")
    var movieImage: String? = "",
    @ColumnInfo(name = "runtime")
    var runtime: String? = "",
    @ColumnInfo(name = "story_line")
    var storyLine: String? = "",
    @ColumnInfo(name = "released_date")
    var releasedDate: String? = "",
    @ColumnInfo(name = "rating")
    var rating: String? = "",
    @ColumnInfo(name = "language")
    var language: String? = "",
    @ColumnInfo(name = "liked")
    var liked: Boolean = false
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var key: Int? = null
}
