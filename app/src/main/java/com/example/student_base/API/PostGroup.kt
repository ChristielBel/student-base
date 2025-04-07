package com.example.student_base.API

import com.example.student_base.data.Group
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

class PostGroup(
    @SerializedName("action")
    val action: Int,

    @SerializedName("group")
    val group: Group
)