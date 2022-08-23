package com.example.todomvc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvc.R
import com.example.todomvc.controller.NoteController
import com.example.todomvc.databinding.TodoNotesRecyclerRowBinding
import com.example.todomvc.model.TodoNotes
import com.example.todomvc.view.noteDialog.NoteDetailsDialog

class TodoNotesAdapter(private val notesMap: MutableMap<String,TodoNotes>): RecyclerView.Adapter<TodoNotesAdapter.TodoNotesVH>() {
    class TodoNotesVH(itemBinding: TodoNotesRecyclerRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val recyclerRowPlanTextView=itemBinding.recyclerRowPlanTextView
        val recyclerRowDateTextView=itemBinding.recyclerRowDateTextView
        val recyclerRowDoneButton=itemBinding.recyclerRowDoneButton
        val recyclerRowOverLine=itemBinding.recyclerRowOverLine
    }
    val noteListValues: MutableCollection<TodoNotes> =notesMap.values
    val noteListKeys: MutableCollection<String> =notesMap.keys
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoNotesVH {
        val itemBinding =
            TodoNotesRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoNotesVH(itemBinding)
    }

    override fun onBindViewHolder(holder: TodoNotesVH, tmpPos: Int) {
        val position=itemCount-tmpPos-1
        holder.recyclerRowPlanTextView.text=noteListValues.elementAt(position).noteTitle
        holder.recyclerRowDateTextView.text=noteListValues.elementAt(position).date.formatDate()
        if(noteListValues.elementAt(position).isDone)
        {
            DrawableCompat.setTint(
                DrawableCompat.wrap(holder.recyclerRowDoneButton.drawable),
                ContextCompat.getColor(holder.itemView.context, R.color.doneButtonColor))
            holder.recyclerRowOverLine.visibility= View.VISIBLE
        }
        else
        {
            DrawableCompat.setTint(
                DrawableCompat.wrap(holder.recyclerRowDoneButton.drawable),
                ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.recyclerRowOverLine.visibility= View.INVISIBLE
        }
        holder.recyclerRowDoneButton.setOnClickListener {
            doneButtonClicked(noteListKeys.elementAt(position))
        }

        holder.recyclerRowPlanTextView.setOnClickListener{
            val activity= it.context as AppCompatActivity
            val dialog= NoteDetailsDialog(notesMap,noteListKeys.elementAt(position))
            dialog.show(activity.supportFragmentManager,"newNoteDialog")
        }
    }

    override fun getItemCount(): Int {
        return this.noteListValues.size
    }
    private fun doneButtonClicked( noteID: String)
    {
        NoteController.todoDoneChanger(noteID)
    }
}