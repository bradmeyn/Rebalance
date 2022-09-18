package com.example.rebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.example.rebalance.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText

//import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // Initialise Firebase Auth
//        auth = Firebase.auth
//        val registerBtn = findViewById<Button>(R.id.registerBtn)
//        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)

        binding.registerBtn.setOnClickListener {
            isValidForm()
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