package com.example.todomvc.view.noteDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todomvc.R
import com.example.todomvc.controller.NoteController
import com.example.todomvc.databinding.FragmentAddNewNoteDialogBinding
import com.example.todomvc.hideKeyboard
import com.example.todomvc.model.CurrentUser
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AddNewNoteDialog() : DialogFragment() {
    private var selectedDateYear: String?=null
    private var selectedDateMonth: String? = null
    private var selectedDateDay: String?=null
    lateinit var binding: FragmentAddNewNoteDialogBinding
    var selectedDate:Long?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddNewNoteDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)

        with(binding)
        {
            newNoteTitleEdit.setOnFocusChangeListener { view, _ -> context?.hideKeyboard(view) }
            newNoteDetailsEdit.setOnFocusChangeListener { view, _ -> context?.hideKeyboard(view) }
        }

        chooseDate()

        saveNewNote()
        binding.newNoteCancelButton.setOnClickListener { dialog!!.dismiss() }
    }

    private fun saveNewNote() {
        binding.saveButton.setOnClickListener {
            if(binding.newNoteTitleEdit.length()==0)
            {
                binding.newNoteTitleLayout.error="Please enter a title"
            }
            else
            {
                if(selectedDate==null)
                {
                    selectedDateDay=""
                    selectedDateMonth=""
                    selectedDateYear=""
                }
                with(binding)
                {
                    NoteController(newNoteTitleEdit.text.toString(),selectedDateDay.toString(),selectedDateMonth!!,selectedDateYear!!,newNoteDetailsEdit.text.toString(),false).uploadNewNote()
                }
                dialog!!.dismiss()
            }
        }
    }

    private fun chooseDate()
    {
        binding.chooseDateButton.setOnClickListener{
            var datePicker: MaterialDatePicker<Long>? =null
            if(selectedDate!=null)
            {
                datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(selectedDate)
                        .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                        .build()
            }
            else
            {
                datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                        .build()
            }
            datePicker.show(parentFragmentManager,"datePicker")
            datePicker.addOnPositiveButtonClickListener {
                selectedDate=datePicker.selection
                selectedDateDay = SimpleDateFormat("dd", Locale.US).format(selectedDate)
                selectedDateMonth = SimpleDateFormat("MM", Locale.US).format(selectedDate)
                selectedDateYear = SimpleDateFormat("yyyy", Locale.US).format(selectedDate)

                binding.chooseTheDateTextView.text="Choose the date: $selectedDateDay/$selectedDateMonth/$selectedDateYear"
            }
        }
    }

}