package com.example.todomvc.view.authScreen

import com.example.todomvc.view.mainScreen.MainActivity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todomvc.R
import com.example.todomvc.databinding.FragmentSignInBinding
import com.example.todomvc.hideKeyboard
import com.example.todomvc.model.CurrentUser
import com.example.todomvc.model.CurrentUser.Companion.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth= CurrentUser.auth!!
        binding=FragmentSignInBinding.inflate(layoutInflater)
        setSignUpTextClickable()
        hideKeyboardOnFocusChange()

        binding.signInFragSignInButton.setOnClickListener {
            if(isEmailPasswordValid()){
                signIn(binding.signInFragEmailInputEdit.text.toString(),binding.signInFragPassInputEdit.text.toString())
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(CurrentUser.auth!!.currentUser!=null )
        {
            CurrentUser.assignVariables()
            updateUI()
        }
    }
    private fun isEmailPasswordValid(): Boolean {
        if(binding.signInFragEmailInputEdit.length()==0)
        {
            binding.signInFragEmailInputLayout.error="Please enter your Email"
            return false
        }
        else
        {
            binding.signInFragEmailInputLayout.error=null
        }
        if(binding.signInFragPassInputEdit.length()==0)
        {
            binding.signInFragPassInputLayout.error="Please enter your Password"
            return false
        }
        else
        {
            binding.signInFragPassInputLayout.error=null
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
    }
    private fun setSignUpTextClickable() {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val navHostFragment = findNavController()
                navHostFragment.navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
        val spannableSignUpText= SpannableString(binding.signInFragNeedAccountTV.text)
        spannableSignUpText.setSpan(clickableSpan,17,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.signInFragNeedAccountTV.text=spannableSignUpText
        binding.signInFragNeedAccountTV.movementMethod = LinkMovementMethod.getInstance();
    }
    private fun hideKeyboardOnFocusChange() {
        binding.signInFragEmailInputEdit.setOnFocusChangeListener{ _, _ -> hideKeyboard() }
        binding.signInFragPassInputEdit.setOnFocusChangeListener { _, _ -> hideKeyboard() }
    }
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    CurrentUser.assignVariables()
                    updateUI()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI() {
        val intent= Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}