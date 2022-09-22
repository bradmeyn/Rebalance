package com.example.rebalance

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.example.rebalance.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding



        // Initialize Firebase Auth
        private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registerBtn.setOnClickListener {
            var validRegistration = true
            validRegistration = isValidForm()

            if(validRegistration){
                var email = binding.emailInput.text.toString()
                var password = binding.passwordInput.text.toString()
                var username = binding.nameInput.text.toString()
                println("user is valid")

                //Create an instance & create a new user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
                { task ->
                    // Sign in success, update UI with the signed-in user's information


                    //if registration is successful
                    if(task.isSuccessful){
                        println("user is created")
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val nameUpdate = userProfileChangeRequest {
                            displayName = username
                        }
                        user!!.updateProfile(nameUpdate).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Name added to profile.")
                            }
                        }
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration has been successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Account creation failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

//        Navigate to login screen
//        val loginLink = findViewById<TextView>(R.id.loginLink)
        binding.loginLink.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }



    private fun emptyInputs(inputs: Array<TextInputEditText>, messages: Array<String>):Boolean{

        var isValid = true
        var counter = 0
        for (input in inputs){

            if(input.text.toString().isBlank()){
                input.setError(messages[counter] + " is required")
                isValid = false
                counter++
            } else {
                counter++
            }

        }
        return isValid
    }

    private fun isValidForm():Boolean {
        val inputs = arrayOf<TextInputEditText>(binding.nameInput,binding.emailInput,binding.passwordInput,binding.confirmInput)
        val messages = arrayOf<String>("Name", "Email", "Password", "Re-entered password");

        var validInputs = true

        //check if inputs are blank
        validInputs = emptyInputs(inputs,messages);

        // check name meets requirements

        // check email format
        if(!binding.emailInput.text.toString().matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))){
            binding.emailInput.setError("Invalid email format")
            validInputs = false
        }

        //check password format
        if(binding.passwordInput.text.toString().trim().length > 7){
            binding.passwordInput.setError("Password must be greater than 7 characters")
            validInputs = false
        }

        //check passwords match
        if(binding.passwordInput.text.toString().trim() != binding.confirmInput.text.toString().trim()){
            binding.confirmInput.setError("Your passwords do not match")
            validInputs = false
            return validInputs
        }
        return validInputs
        //check if email exists (firebase) might do this
    }




}