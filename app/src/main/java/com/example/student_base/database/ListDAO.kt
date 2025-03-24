package com.example.student_base.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.student_base.data.Faculty
import kotlinx.coroutines.flow.Flow

interface ListDAO {
    @Query("SELECT * FROM Faculty order by faculty_name")
    fun getFaculty(): Flow<List<Faculty>>

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaculty(faculty: Faculty)

    @Update(entity = Faculty::class)
    suspend fun updateFaculty(faculty: Faculty)

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFaculty(facultyList: List<Faculty>)

    @Delete(entity = Faculty::class)
    suspend fun deleteFaculty(faculty: Faculty)

    @Query("DELETE FROM Faculty")
    suspend fun deleteAllFaculty()
}