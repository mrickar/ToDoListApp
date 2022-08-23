package com.example.todomvc.view.mainScreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todomvc.databinding.ActivityMainBinding
import com.example.todomvc.R
import com.example.todomvc.adapter.TodoNotesAdapter
import com.example.todomvc.view.noteDialog.AddNewNoteDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFrag:NavHostFragment =supportFragmentManager.findFragmentById(R.id.mainNavigationContainer) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFrag.navController)

    }
}