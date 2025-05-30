package com.example.student_base.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

import java.util.UUID

@Entity(
    indices = [Index("id")]
)

@Serializable
data class Faculty(
    @SerializedName("id") @PrimaryKey val id: UUID = UUID.randomUUID(),
    @SerializedName("name") @ColumnInfo(name = "faculty_name") var name: String = ""
)