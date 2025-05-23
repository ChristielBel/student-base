package com.example.student_base.repository

import com.example.student_base.data.Faculty
import com.example.student_base.data.Group
import com.example.student_base.data.Student
import com.example.student_base.database.ListDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class OfflineDBRepository(val dao: ListDAO) : DBRepository {

    override fun getFaculty(): Flow<List<Faculty>> = dao.getFaculty()
    override suspend fun insertFaculty(faculty: Faculty) = dao.insertFaculty(faculty)
    override suspend fun updateFaculty(faculty: Faculty) = dao.updateFaculty(faculty)
    override suspend fun insertAllFaculty(facultyList: List<Faculty>) =
        dao.insertAllFaculty(facultyList)

    override suspend fun deleteFaculty(faculty: Faculty) = dao.deleteFaculty(faculty)
    override suspend fun deleteAllFaculty() = dao.deleteAllFaculty()

    override fun getAllGroups(): Flow<List<Group>> = dao.getAllGroups()
    override fun getFacultyGroups(facultyID: UUID): Flow<List<Group>> =
        dao.getFacultyGroups(facultyID)

    override suspend fun insertGroup(group: Group) = dao.insertGroup(group)
    override suspend fun deleteGroup(group: Group) = dao.deleteGroup(group)
    override suspend fun deleteAllGroups() = dao.deleteAllGroups()

    override fun getAllStudents(): Flow<List<Student>> = dao.getAllStudents()
    override fun getGroupStudents(groupID: UUID): Flow<List<Student>> =
        dao.getGroupStudents(groupID)

    override suspend fun insertStudent(student: Student) = dao.insertStudent(student)
    override suspend fun deleteStudent(student: Student) = dao.deleteStudent(student)
    override suspend fun deleteAllStudents() = dao.deleteAllStudents()
}