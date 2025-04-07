package com.example.student_base.API

import com.example.student_base.data.Faculty
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class PostFaculty(
    @SerializedName("action")
    val action: Int,

    @SerializedName("faculty")
    val faculty: Faculty
)