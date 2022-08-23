package com.example.todomvc.controller

import android.view.View
import androidx.compose.runtime.Composable
import androidx.core.view.MenuHost
import com.example.todomvc.adapter.TodoNotesAdapter
import com.example.todomvc.databinding.FragmentNotesScreenBinding
import com.example.todomvc.model.CurrentUser
import com.example.todomvc.model.SelectedDateFormatter
import com.example.todomvc.model.TodoNotes
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class NoteController(
    private var noteTitle:String,
    private var day: String,
    private var month: String,
    private var year: String,
    private var noteDetailed:String,
    private var isDone:Boolean,
    ) {
    constructor(hashMap: HashMap<String,Serializable>) : this(hashMap["noteTitle"] as String,
        (hashMap["date"] as HashMap<String,String>)["day"] as String,
        (hashMap["date"] as HashMap<String,String>)["month"] as String,
        (hashMap["date"] as HashMap<String,String>)["year"] as String,
        hashMap["noteDetailed"] as String,
        hashMap["done"] as Boolean)

    val ToDoNote=TodoNotes(noteTitle,SelectedDateFormatter(day,month,year),noteDetailed,isDone)

    fun uploadNewNote()
    {
        val databaseReference = Firebase.database.reference.child("users/${CurrentUser.uid}/Notes")
        databaseReference.push().setValue(ToDoNote)
    }
    companion object{
        fun updateNoteDetails(noteID:String,details:String)
        {
            Firebase.database.reference.child("users/${CurrentUser.uid!!}/Notes/${noteID}/noteDetailed").setValue(details)
        }
        fun todoDoneChanger(noteID:String)
        {
            Firebase.database.reference.child("users/${CurrentUser.uid!!}/Notes/${noteID}/done").get().addOnSuccessListener {
                Firebase.database.reference.child("users/${CurrentUser.uid!!}/Notes/${noteID}/done").setValue(!(it.value as Boolean))
            }
        }

        fun deleteNote(noteID:String) {
            Firebase.database.reference.child("users/${CurrentUser.uid!!}/Notes/${noteID}").removeValue()
        }
        fun setNotes(noteListMap:MutableMap<String,TodoNotes>, binding: FragmentNotesScreenBinding,adapter: TodoNotesAdapter) {
            val noNotesLinearLayout=binding.noNotesLinearLayout
            val childEventListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val newNote=NoteController(snapshot.value as HashMap<String, Serializable> ).ToDoNote

                    noteListMap.put(snapshot.key as String,newNote)

                    adapter.notifyItemInserted(0)
                    adapter.notifyItemRangeChanged(0,noteListMap.size)
                    binding.recyclerViewNotes.scrollToPosition(0)
                    noNotesLinearLayout.visibility= View.INVISIBLE
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    noteListMap[snapshot.key as String]=NoteController(snapshot.value as HashMap<String, Serializable> ).ToDoNote
                    adapter.notifyItemChanged(adapter.itemCount-adapter.noteListKeys.indexOf(snapshot.key)-1)

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    var ind=0
                    for (key in noteListMap.keys)
                    {
                        if(key==snapshot.key) break
                        ind+=1
                    }
                    noteListMap.remove(snapshot.key)
                    adapter.notifyItemRemoved(ind-1)
                    adapter.notifyItemRangeChanged(0,noteListMap.size)
                    if(adapter.itemCount==0) binding.noNotesLinearLayout.visibility= View.VISIBLE
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            Firebase.database.reference.child("users/${CurrentUser.uid!!}/Notes").addChildEventListener(childEventListener)
        }
    }

}