package com.example.student_base.interfaces

import com.example.student_base.NamesOfFragment
import com.example.student_base.data.Student

interface MainActivityCallbacks {
    fun newTitle(_title: String)
    fun showFragment(fragmentType: NamesOfFragment, student: Student? = null)
}