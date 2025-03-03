package com.example.student_base.data

import java.util.UUID

data class Group(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var facultyID: UUID? = null
)
