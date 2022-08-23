package com.example.todomvc.model

data class TodoNotes(
    var noteTitle:String,
    var date: SelectedDateFormatter,
    var noteDetailed:String,
    var isDone:Boolean,
    ) {
}

