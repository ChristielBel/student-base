package com.example.student_base.API

import com.example.student_base.data.Student
import com.google.gson.annotations.SerializedName

class PostStudent(
    @SerializedName("action")
    val action: Int,

    @SerializedName("student")
    val student: Student
)