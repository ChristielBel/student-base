package com.example.student_base.repository

import androidx.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.student_base.API.APPEND_FACULTY
import com.example.student_base.API.APPEND_GROUP
import com.example.student_base.API.APPEND_STUDENT
import com.example.student_base.API.DELETE_FACULTY
import com.example.student_base.API.DELETE_GROUP
import com.example.student_base.API.DELETE_STUDENT
import com.example.student_base.API.ListAPI
import com.example.student_base.API.ListConnection
import com.example.student_base.API.PostFaculty
import com.example.student_base.API.PostGroup
import com.example.student_base.API.PostResult
import com.example.student_base.API.PostStudent
import com.example.student_base.API.UPDATE_FACULTY
import com.example.student_base.API.UPDATE_GROUP
import com.example.student_base.API.UPDATE_STUDENT
import com.example.student_base.MyApplication
import com.example.student_base.MyConsts.TAG
import com.example.student_base.R
import com.example.student_base.data.Faculties
import com.example.student_base.data.Faculty
import com.example.student_base.data.Group
import com.example.student_base.data.Groups
import com.example.student_base.data.ListOfFaculty
import com.example.student_base.data.ListOfGroup
import com.example.student_base.data.ListOfStudent
import com.example.student_base.data.Student
import com.example.student_base.data.Students
import com.example.student_base.database.ListDatabase
import java.util.UUID
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException


class AppRepository {
    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(): AppRepository {
            if (INSTANCE == null) {
                INSTANCE = AppRepository()
            }
            return INSTANCE ?: throw IllegalStateException("Репозиторий не инициализирован")
        }
    }

    //    var listOfFaculty: MutableLiveData<ListOfFaculty?> = MutableLiveData()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()

    //    var listOfGroup: MutableLiveData<ListOfGroup?> = MutableLiveData()
    var group: MutableLiveData<Group> = MutableLiveData()

    //    var listOfStudent: MutableLiveData<ListOfStudent?> = MutableLiveData()
    var student: MutableLiveData<Student> = MutableLiveData()

//    fun addFaculty(faculty: Faculty) {
//        val listTmp = (listOfFaculty.value ?: ListOfFaculty()).apply {
//            items.add(faculty)
//        }
//        listOfFaculty.postValue(listTmp)
//        setCurrentFaculty(faculty)
//    }

    fun getFacultyPosition(faculty: Faculty): Int = listOfFaculty.value?.indexOfFirst {
        it.id == faculty.id
    } ?: -1

    fun getFacultyPosition() = getFacultyPosition(faculty.value ?: Faculty())

    fun setCurrentFaculty(position: Int) {
        if (listOfFaculty.value == null || position < 0 ||
            (listOfFaculty.value?.size!! <= position)
        )
            return
        setCurrentFaculty(listOfFaculty.value!![position])
    }

    fun setCurrentFaculty(_faculty: Faculty) {
        faculty.postValue(_faculty)
    }

    /* fun updateFaculty(faculty: Faculty) {
         val position = getFacultyPosition(faculty)
         if (position < 0) addFaculty(faculty)
         else {
             val listTmp = listOfFaculty.value!!
             listTmp.items[position] = faculty
             listOfFaculty.postValue(listTmp)
         }
     }

     fun deleteFaculty(faculty: Faculty) {
         val listTmp = listOfFaculty.value!!
         if (listTmp.items.remove(faculty)) {
             listOfFaculty.postValue(listTmp)
         }
         setCurrentFaculty(0)
     }*/

    fun saveData() {
        /* val context = MyApplication.context
         val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
         sharedPreferences.edit().apply {
             val gson = Gson()
             val lst = listOfFaculty.value?.items ?: listOf<Faculty>()
             val jsonString = gson.toJson(lst)
             Log.d(TAG, "Сохранение $jsonString")
             putString(
                 context.getString(R.string.preference_key_faculty_list),
                 jsonString
             )
             putString(
                 context.getString(R.string.preference_key_group_list),
                 gson.toJson(listOfGroup.value?.items ?: listOf<Group>())
             )
             putString(
                 context.getString(R.string.preference_key_students_list),
                 gson.toJson(listOfStudent.value?.items ?: listOf<Student>())
             )
             apply()
         }*/
    }

    fun loadData() {
        /*  val context = MyApplication.context
          val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
          sharedPreferences.apply {
              val jsonString =
                  getString(context.getString(R.string.preference_key_faculty_list), null)
              if (jsonString != null) {
                  Log.d(TAG, "Чтение $jsonString")
                  val listType = object : TypeToken<List<Faculty>>() {}.type
                  val tempList = Gson().fromJson<List<Faculty>>(jsonString, listType)
                  val temp = ListOfFaculty()
                  temp.items = tempList.toMutableList()
                  Log.d(TAG, "Загрузка ${temp.toString()}")
                  listOfFaculty.postValue(temp)

              }
              val jsonStringG = getString(context.getString(R.string.preference_key_group_list), null)
              if (jsonStringG != null) {
                  val listTypeG = object : TypeToken<List<Group>>() {}.type
                  val tempListG = Gson().fromJson<List<Group>>(jsonStringG, listTypeG)
                  val tempG = ListOfGroup()
                  tempG.items = tempListG.toMutableList()
                  listOfGroup.postValue(tempG)

              }
              val jsonStringS =
                  getString(context.getString(R.string.preference_key_students_list), null)
              if (jsonStringS != null) {
                  val listTypeS = object : TypeToken<List<Student>>() {}.type
                  val tempListS = Gson().fromJson<List<Student>>(jsonStringS, listTypeS)
                  val tempS = ListOfStudent()
                  tempS.items = tempListS.toMutableList()
                  listOfStudent.postValue(tempS)

              }
          }*/
        fetchFaculties()
    }

    /* fun addGroup(group: Group) {
         val listTmp = (listOfGroup.value ?: ListOfGroup()).apply {
             items.add(group)
         }
         listOfGroup.postValue(listTmp)
         setCurrentGroup(group)
     }*/

    fun getGroupPosition(group: Group): Int = listOfGroup.value?.indexOfFirst {
        it.id == group.id
    } ?: -1

    fun getGroupPosition() = getGroupPosition(group.value ?: Group())

    fun setCurrentGroup(position: Int) {
        if (listOfGroup.value == null || position < 0 ||
            (listOfGroup.value?.size!! <= position)
        )
            return
        setCurrentGroup(listOfGroup.value!![position])
    }

    fun setCurrentGroup(_group: Group) {
        group.postValue(_group)
    }

    /*fun updateGroup(group: Group) {
        val position = getGroupPosition(group)
        if (position < 0) addGroup(group)
        else {
            val listTmp = listOfGroup.value!!
            listTmp.items[position] = group
            listOfGroup.postValue(listTmp)
        }
    }

    fun deleteGroup(group: Group) {
        val listTmp = listOfGroup.value ?: ListOfGroup()
        if (listTmp.items.remove(group))
            listOfGroup.postValue(listTmp)
        setCurrentGroup(0)
    }*/

    val facultyGroups
        get() = listOfGroup.value?.filter {
            it.facultyID == (faculty.value?.id ?: 0)
        }?.sortedBy { it.name } ?: listOf()

    fun getFacultyGroups(facultyID: UUID) =
        (listOfGroup.value?.filter { it.facultyID == facultyID }?.sortedBy { it.name }
            ?: listOf())


    /* fun addStudent(student: Student) {
         val listTmp = (listOfStudent.value ?: ListOfStudent()).apply {
             items.add(student)
         }
         listOfStudent.postValue(listTmp)
         setCurrentStudent(student)
     }*/

    fun getStudentPosition(student: Student): Int = listOfStudent.value?.indexOfFirst {
        it.id == student.id
    } ?: -1

    fun getStudentPosition() = getStudentPosition(student.value ?: Student())

    fun setCurrentStudent(position: Int) {
        if (listOfStudent.value == null || position < 0 ||
            (listOfStudent.value?.size!! <= position)
        )
            return
        setCurrentStudent(listOfStudent.value!![position])
    }

    fun setCurrentStudent(_student: Student) {
        student.postValue(_student)
    }

    /*fun updateStudent(student: Student) {
        val position = getStudentPosition(student)
        if (position < 0) addStudent(student)
        else {
            val listTmp = listOfStudent.value!!
            listTmp.items[position] = student
            listOfStudent.postValue(listTmp)
        }
    }

    fun deleteStudent(student: Student) {
        val listTmp = listOfStudent.value ?: ListOfStudent()
        if (listTmp.items.remove(student)) listOfStudent.postValue(listTmp)
        setCurrentStudent(0)
    }*/

    val groupStudents
        get() = listOfStudent.value?.filter {
            it.groupID == (group.value?.id ?: 0)
        }?.sortedBy { it.shortName } ?: listOf()

    fun getGroupStudents(groupID: UUID) =
        listOfStudent.value?.filter { it.groupID == groupID }?.sortedBy { it.shortName }
            ?: listOf()

    private val listDB by lazy {
        OfflineDBRepository(
            ListDatabase.getDatabase(MyApplication.context).listDAO()
        )
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy() {
        myCoroutineScope.cancel()
    }

    val listOfFaculty: LiveData<List<Faculty>> = listDB.getFaculty()
        .asLiveData()

    private fun updateFaculties(postFaculty: PostFaculty) {
        listAPI.postFaculty(postFaculty)
            .enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if (response.code() == 200) fetchFaculties()
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка записи факультета", t)
                }
            })
    }

    fun addFaculty(faculty: Faculty) {
        updateFaculties(PostFaculty(APPEND_FACULTY, faculty))
    }

    fun updateFaculty(faculty: Faculty) {
        updateFaculties(PostFaculty(UPDATE_FACULTY, faculty))
    }

    fun deleteFaculty(faculty: Faculty) {
        updateFaculties(PostFaculty(DELETE_FACULTY, faculty))
    }

    val listOfGroup: LiveData<List<Group>> = listDB.getAllGroups().asLiveData()

    private fun updateGroups(postGroup: PostGroup) {
        listAPI.postGroup(postGroup)
            .enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if (response.code() == 200) fetchGroups()
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка записи группы", t)
                }
            })
    }

    fun addGroup(group: Group) {
        updateGroups(PostGroup(APPEND_GROUP, group))
    }

    fun updateGroup(group: Group) {
        updateGroups(PostGroup(UPDATE_GROUP, group))
    }

    fun deleteGroup(group: Group) {
        updateGroups(PostGroup(DELETE_GROUP, group))
    }

    var listOfStudent: LiveData<List<Student>> = listDB.getAllStudents().asLiveData()

    private fun updateStudents(postStudent: PostStudent) {
        listAPI.postStudent(postStudent)
            .enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if (response.code() == 200) fetchStudents()
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка записи студента", t)
                }
            })
    }

    fun addStudent(student: Student) {
        updateStudents(PostStudent(APPEND_STUDENT, student))
    }

    fun updateStudent(student: Student) {
        updateStudents(PostStudent(UPDATE_STUDENT, student))
    }

    fun deleteStudent(student: Student) {
        updateStudents(PostStudent(DELETE_STUDENT, student))
    }

    private var listAPI = ListConnection.getClient().create(ListAPI::class.java)

    fun fetchFaculties() {
        listAPI.getFaculties().enqueue(object : Callback<Faculties> {
            override fun onFailure(call: Call<Faculties>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка факультетов", t)
            }

            override fun onResponse(call: Call<Faculties>, response: Response<Faculties>) {
                if (response.code() == 200) {
                    val faculties = response.body()
                    val items = faculties?.items ?: emptyList()
                    Log.d(TAG, "Получен список факультетов $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllFaculty()
                        for (f in items)
                            listDB.insertFaculty(f)
                    }
                }
                fetchGroups()
            }
        })
    }

    fun fetchGroups() {
        listAPI.getGroups().enqueue(object : Callback<Groups> {
            override fun onFailure(call: Call<Groups>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка групп", t)
            }

            override fun onResponse(call: Call<Groups>, response: Response<Groups>) {
                if (response.code() == 200) {
                    val groups = response.body()
                    val items = groups?.items ?: emptyList()
                    Log.d(TAG, "Получен список групп $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllGroups()
                        for (g in items)
                            listDB.insertGroup(g)
                    }
                }
                fetchStudents()
            }
        })
    }

    fun fetchStudents() {
        listAPI.getStudents().enqueue(object : Callback<Students> {
            override fun onFailure(call: Call<Students>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка студентов", t)
            }

            override fun onResponse(call: Call<Students>, response: Response<Students>) {
                if (response.code() == 200) {
                    val students = response.body()
                    val items = students?.items ?: emptyList()
                    Log.d(TAG, "Получен список студентов $items")
                    myCoroutineScope.launch {
                        listDB.deleteAllStudents()
                        for (s in items)
                            listDB.insertStudent(s)
                    }
                }
            }
        })
    }
}
