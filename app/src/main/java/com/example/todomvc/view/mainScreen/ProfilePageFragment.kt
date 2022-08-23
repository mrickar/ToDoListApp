package com.example.todomvc.view.mainScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todomvc.R
import com.example.todomvc.databinding.FragmentProfilePageBinding
import com.example.todomvc.model.CurrentUser
import com.example.todomvc.view.authScreen.AuthActivity
import com.google.firebase.auth.FirebaseAuth


class ProfilePageFragment : Fragment() {
    lateinit var binding: FragmentProfilePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfilePageBinding.inflate(inflater, container, false)

        binding.userEmailAddressText.text=CurrentUser.user?.email

        binding.signOutButton.setOnClickListener {
            CurrentUser.signOut()
            goAuthActivity()
        }

        return binding.root
    }
    private fun goAuthActivity() {
        val intent= Intent(context, AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}