package com.example.student_base.data

import com.google.gson.annotations.SerializedName

class Students {
    @SerializedName("items") lateinit var items : List<Student>
}