package com.example.student_base.API

import com.google.gson.annotations.SerializedName

class PostResult {
    @SerializedName("result")
    lateinit var resultString: String
}