package com.example.student_base.data

import java.util.UUID

data class Faculty(
    val id: UUID = UUID.randomUUID(),
    var name: String = ""
)