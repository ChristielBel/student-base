package com.example.student_base.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = "groups",
    indices = [Index("id"), Index("faculty_id")],
    foreignKeys = [
        ForeignKey(
            entity = Faculty::class,
            parentColumns = ["id"],
            childColumns = ["faculty_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Group(
    @SerializedName("id") @PrimaryKey val id: UUID = UUID.randomUUID(),
    @SerializedName("name") @ColumnInfo(name = "group_name") var name: String = "",
    @SerializedName("faculty_id") @ColumnInfo(name = "faculty_id") var facultyID: UUID? = null
)