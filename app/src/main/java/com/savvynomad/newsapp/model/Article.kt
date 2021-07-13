package com.savvynomad.newsapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName = "articles_table")
data class Article(
    @PrimaryKey
    @NonNull
    val url: String,
    val author: String,
    val content: String?,
    val description: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    val source: Source,
    val title: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String
) : Parcelable