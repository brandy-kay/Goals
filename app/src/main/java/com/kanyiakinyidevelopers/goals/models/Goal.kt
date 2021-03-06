package com.kanyiakinyidevelopers.goals.models


data class Goal(
    val postId: String? = null,
    val goalTitle: String? = null,
    val goalDescription: String? = null,
    val goalBgColor: String? = null,
    val dateTime: String? = null,
    val poster: String? = null,
    val isAchieved: Boolean = false
)

