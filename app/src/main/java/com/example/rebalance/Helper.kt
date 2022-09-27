package com.example.rebalance

import com.google.android.material.textfield.TextInputEditText

object Helper {
    @JvmStatic
    fun noEmptyInputs(inputs: Array<TextInputEditText>, messages: Array<String>):Boolean{

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

}

