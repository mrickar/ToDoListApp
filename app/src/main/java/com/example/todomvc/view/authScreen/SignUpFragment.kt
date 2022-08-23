package com.example.todomvc.view.authScreen


import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todomvc.databinding.FragmentSignUpBinding
import com.example.todomvc.hideKeyboard
import com.example.todomvc.model.CurrentUser
import com.example.todomvc.view.mainScreen.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth= CurrentUser.auth!!
        binding=FragmentSignUpBinding.inflate(inflater, container, false)

        setSignInTextClickable()

        hideKeyboardOnFocusChange()

        binding.signUpFragSignUpButton.setOnClickListener {
            if(isEmailPasswordValid())
            {
                signUp(binding.signUpFragEmailInputEdit.text.toString(),binding.signUpFragPassInputEdit.text.toString())
            }
        }

        return binding.root
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    CurrentUser.assignVariables()
                    val databaseReference = Firebase.database.reference.child("users").child(CurrentUser.uid!!)
                    databaseReference.child("uid").setValue(CurrentUser.uid)
//                    databaseReference.child("TODO_counter").setValue(0)
                    updateUI()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(){
            val intent= Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
    }
    private fun isEmailPasswordValid(): Boolean {
        if(binding.signUpFragEmailInputEdit.length()==0)
        {
            binding.signUpFragEmailInputLayout.error="Please enter an Email"
            return false
        }
        else
        {
            binding.signUpFragEmailInputLayout.error=null
        }
        if(binding.signUpFragPassInputEdit.length()==0)
        {
            binding.signUpFragPassInputLayout.error="Please enter a Password"
            return false
        }
        else
        {
            binding.signUpFragPassInputLayout.error=null
        }
        if(binding.signUpFragPassAgainInputEdit.length()==0)
        {
            binding.signUpFragPassAgainInputLayout.error="Please enter Password again"
            return false
        }
        else
        {
            binding.signUpFragPassAgainInputLayout.error=null
        }
        if(binding.signUpFragPassInputEdit.text.toString()!=binding.signUpFragPassAgainInputEdit.text.toString())
        {
            binding.signUpFragPassAgainInputLayout.error="Passwords does not match"
            return false
        }
        else
        {
            binding.signUpFragPassAgainInputLayout.error=null
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
    }
    private fun setSignInTextClickable() {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val navHostFragment = findNavController()
                navHostFragment.navigate(com.example.todomvc.R.id.action_signUpFragment_to_signInFragment)
            }

        }
        val spannableSignInText= SpannableString(binding.signUpFragHaveAccountTV.text)
        spannableSignInText.setSpan(clickableSpan,17,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.signUpFragHaveAccountTV.text=spannableSignInText
        binding.signUpFragHaveAccountTV.movementMethod = LinkMovementMethod.getInstance();
    }


    private fun hideKeyboardOnFocusChange() {
        binding.signUpFragEmailInputEdit.setOnFocusChangeListener{ _, _ -> hideKeyboard() }
        binding.signUpFragPassInputEdit.setOnFocusChangeListener { _, _ -> hideKeyboard() }
        binding.signUpFragPassAgainInputEdit.setOnFocusChangeListener { _, _ -> hideKeyboard() }
    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}