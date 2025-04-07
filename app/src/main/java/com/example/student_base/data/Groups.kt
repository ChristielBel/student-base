package com.example.student_base.data

import com.google.gson.annotations.SerializedName

class Groups {
    @SerializedName("items") lateinit var items : List<Group>
}