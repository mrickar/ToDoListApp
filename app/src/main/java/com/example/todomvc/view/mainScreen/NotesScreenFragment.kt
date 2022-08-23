package com.example.todomvc.view.mainScreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todomvc.R
import com.example.todomvc.adapter.TodoNotesAdapter
import com.example.todomvc.controller.NoteController
import com.example.todomvc.databinding.FragmentNotesScreenBinding
import com.example.todomvc.model.CurrentUser
import com.example.todomvc.model.TodoNotes
import com.example.todomvc.view.noteDialog.AddNewNoteDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.Serializable


class NotesScreenFragment : Fragment() {

    lateinit var binding: FragmentNotesScreenBinding
    lateinit var adapter: TodoNotesAdapter
    var noteListMap:MutableMap<String,TodoNotes> = mutableMapOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNotesScreenBinding.inflate(inflater, container, false)
        menuOnClick()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        setUI()
        if (noteListMap.isEmpty()) NoteController.setNotes(noteListMap,binding,adapter)//setNotes()
    }

    private fun setUI() {
        adapter=TodoNotesAdapter(noteListMap)
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewNotes.context,
                DividerItemDecoration.VERTICAL
            )
        )
        if(noteListMap.isNotEmpty()) binding.noNotesLinearLayout.visibility=View.INVISIBLE
    }

    private fun menuOnClick() {
        binding.toolbar.setOnMenuItemClickListener{
            when(it.itemId)
            {
                R.id.addNoteButton ->
                {
                    val dialog= AddNewNoteDialog()
                    dialog.show((activity as FragmentActivity).supportFragmentManager,"addNewNoteDialog")
                    true
                }
                else -> {false}
            }
        }
    }


}