package com.example.student_base.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.student_base.data.Faculty
import com.example.student_base.data.Group
import com.example.student_base.data.Student

@Database(
    entities = [Faculty::class,
        Group::class,
        Student::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListTypeConverters::class)
abstract class ListDatabase : RoomDatabase() {

    abstract fun listDAO(): ListDAO

    companion object {
        @Volatile
        private var INSTANCE: ListDatabase? = null
        fun getDatabase(context: Context): ListDatabase {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ListDatabase::class.java,
            "list_database"
        ).fallbackToDestructiveMigration().build()
    }
}