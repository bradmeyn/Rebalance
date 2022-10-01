package com.example.rebalance.views

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rebalance.database.AppDatabase
import com.example.rebalance.Helper.noEmptyInputs
import com.example.rebalance.models.User
import com.example.rebalance.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText

import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        appDatabase = AppDatabase.getDatabase(this)

        binding.registerBtn.setOnClickListener {
            var validRegistration = true
            validRegistration = isValidForm()

            if(validRegistration){
                var email = binding.emailInput.text.toString()
                var password = binding.passwordInput.text.toString()
                var username = binding.nameInput.text.toString()

                //Create an instance & create a new user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
                { task ->
                    // Sign in success, update UI with the signed-in user's information

                    //if registration is successful
                    if(task.isSuccessful){

                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        val userId = user!!.uid

                        val nameUpdate = userProfileChangeRequest {
                            displayName = username
                        }
                        user!!.updateProfile(nameUpdate).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Name added to profile.")

                                createUser(userId, username)
                            }
                        }
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration has been successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id",userId)
                        intent.putExtra("user_name",username)
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
        binding.loginLink.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }



    private fun isValidForm():Boolean {
        val inputs = arrayOf<TextInputEditText>(binding.nameInput,binding.emailInput,binding.passwordInput,binding.confirmInput)
        val messages = arrayOf<String>("Name", "Email", "Password", "Re-entered password");

        var validInputs = true

        //check if inputs are blank
        validInputs = noEmptyInputs(inputs,messages);

        // check name meets requirements
        if(!binding.nameInput.text.toString().matches(Regex("[a-zA-Z]+"))){
            binding.nameInput.setError("Invalid name format")
            Toast.makeText(baseContext, "Invalid name format.",
                Toast.LENGTH_SHORT).show()
            validInputs = false
        }

        // check email format
        if(!binding.emailInput.text.toString().matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))){
            binding.emailInput.setError("Invalid email format")
            Toast.makeText(baseContext, "Invalid email format.",
                Toast.LENGTH_SHORT).show()
            validInputs = false
        }

        //check password format
        if(binding.passwordInput.text.toString().trim().length > 6){
            binding.passwordInput.setError("Password must be greater than 6 characters")
            validInputs = false
        }

        //check passwords match
        if(binding.passwordInput.text.toString().trim() != binding.confirmInput.text.toString().trim()){
            binding.confirmInput.setError("Your passwords do not match")
            Toast.makeText(baseContext, "Your passwords do not match.",
                Toast.LENGTH_SHORT).show()
            validInputs = false
        }
        return validInputs
        //check if email exists (firebase) might do this
    }

    private fun createUser(id: String, name: String){

        val newUser = User(id, name)
        GlobalScope.launch(Dispatchers.IO){
            appDatabase.userDao().addUser(newUser)
        }

        Toast.makeText(
            this@RegisterActivity,
            "User saved to database",
            Toast.LENGTH_SHORT
        ).show()
    }




}