package com.example.rebalance

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.HeaderViewListAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.rebalance.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth
    private lateinit var appDatabase: AppDatabase
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDatabase = AppDatabase.getDatabase(this)
        auth = Firebase.auth

        val registerLink = findViewById<TextView>(R.id.registerLink)
        registerLink.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        binding.loginBtn.setOnClickListener{
            var email = binding.emailInput.text.toString()
            var password = binding.passwordInput.text.toString()

            var isValid = isValidForm()

            if(isValid){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser

                            val userId = user!!.uid
                            println("Checking if IDs match")
                            println(userId)
                            println(userId.equals("Mwa5PLoqfveqQRW5CCUZ9dvKULz1"))

                            val username = user!!.displayName
                            if (user != null) {
                                Toast.makeText(baseContext, "Welcome back "+ user.displayName,
                                    Toast.LENGTH_SHORT).show()
                                println("Searching in db for user.....")
//                            getUser(user.uid)
                            }


                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id",userId)
                            intent.putExtra("user_name",username)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "The credentials you have entered are incorrect.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }
    }

    private fun isValidForm():Boolean {
        val inputs = arrayOf<TextInputEditText>(binding.emailInput,binding.passwordInput)
        val messages = arrayOf<String>("Email", "Password");

        var validInputs = true

        //check if inputs are blank
        validInputs = Helper.noEmptyInputs(inputs, messages);

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

        return validInputs
        //check if email exists (firebase) might do this
    }








}

