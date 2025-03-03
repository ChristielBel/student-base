package com.example.student_base.fragmets

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.student_base.MainActivity
import com.example.student_base.R
import com.example.student_base.databinding.FragmentFacultyBinding
import com.example.student_base.interfaces.MainActivityCallbacks

class FacultyFragment : Fragment() {

    companion object {
        private var INSTANCE: FacultyFragment? = null
        fun getInstance(): FacultyFragment {
            if (INSTANCE == null) INSTANCE = FacultyFragment()
            return INSTANCE ?: throw Exception("FacultyFragment не создан")
        }
    }

    private lateinit var viewModel: FacultyViewModel
    private lateinit var _binding: FragmentFacultyBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ma = (requireActivity() as MainActivityCallbacks)
        ma.newTitle("СПИСОК ФАКУЛЬТЕТОВ")
        _binding = FragmentFacultyBinding.inflate(inflater, container, false)
        binding.rvFaculty.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyViewModel::class.java)
        viewModel.facultyList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvFaculty.adapter = FacultyAdapter(it.items)
            }
        }
    }

    override fun append() {
        editFaculty()
    }

    override fun update() {
        editFaculty(viewModel.faculty?.name ?: "")
    }

    override fun delete() {
        deleteDialog()
    }

    private fun deleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage(
                "Вы действительно хотите удалить факультет " +
                        "${viewModel.faculty?.name ?: ""}?"
            )
            .setPositiveButton("ДА") { _, _ ->
                viewModel.deleteFaculty()
            }
            .setNegativeButton("НЕТ!", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun editFaculty(facultyName: String = "") {
        val mDialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_string, null)
        val messageText = mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString = mDialogView.findViewById<EditText>(R.id.etString)
        inputString.setText(facultyName)
        messageText.text = "Укажите наименование факультета"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("ИЗМЕНЕНИЕ ДАННЫХ")
            .setView(mDialogView)
            .setPositiveButton("Подтверждаю") { _, _ ->
                if (inputString.text.isNotBlank()) {
                    if (facultyName.isBlank())
                        viewModel.appendFaculty(inputString.text.toString())
                    else
                        viewModel.updateFaculty(inputString.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }


}