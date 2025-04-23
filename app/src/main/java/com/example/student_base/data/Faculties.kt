package com.example.student_base.data

import com.google.gson.annotations.SerializedName

class Faculties {
    @SerializedName("items")
    lateinit var items: List<Faculty>
}