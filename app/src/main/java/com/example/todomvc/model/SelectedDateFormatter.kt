package com.example.todomvc.model

class SelectedDateFormatter(
    val day: String,
    val month: String,
    val year: String,): SelectedDate(day,month,year) {

    fun formatDate():String
    {
        if(selectedDateDay=="" &&selectedDateMonth==""&&selectedDateYear=="") return ""
        else return "$selectedDateDay/$selectedDateMonth/$selectedDateYear"
    }
}