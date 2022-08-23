package com.example.todomvc.view.noteDialog

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todomvc.R
import com.example.todomvc.controller.NoteController
import com.example.todomvc.databinding.FragmentNoteDetailsDialogBinding
import com.example.todomvc.model.TodoNotes

class NoteDetailsDialog(val notesMap:MutableMap<String,TodoNotes>,val key:String) : DialogFragment() {
    lateinit var binding: FragmentNoteDetailsDialogBinding
    val clickedNote= notesMap[key]
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentNoteDetailsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        if (clickedNote!=null)
        {
            binding.noteDetailsDialogTitle.text= clickedNote.noteTitle
            binding.noteDetailsDialogDetails.setText( clickedNote.noteDetailed)
            binding.noteDetailsDialogDate.text=clickedNote.date.formatDate()
            binding.editButton.setOnClickListener {
                if (!binding.noteDetailsDialogDetails.isEnabled)
                {
                    binding.noteDetailsDialogDetails.isEnabled = true
                    binding.editButton.setImageResource(R.drawable.ic_save)
                }
                else
                {
                    binding.noteDetailsDialogDetails.isEnabled=false
                    NoteController.updateNoteDetails(key,binding.noteDetailsDialogDetails.text.toString())
                    binding.editButton.setImageResource(R.drawable.ic_edit)
                }
            }
            binding.noteDetailsDialogClose.setOnClickListener {
                dismiss()
            }

        }
        binding.noteDetailsDialogDelete.setOnClickListener{
            NoteController.deleteNote(key)
            dismiss()
        }
    }

}