package com.example.rebalance

import com.google.android.material.textfield.TextInputEditText
import java.lang.NumberFormatException
import java.math.BigDecimal

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

    @JvmStatic
    fun isValidPrice(input: TextInputEditText):Boolean{
        var validPrice = true
        println(input.text.toString().toBigDecimalOrNull())
        if(input.text.toString().toBigDecimalOrNull() == null){
            println("Is not a valid number")
            input.setError("Invalid number format")
            validPrice = false
            return validPrice
        }
        if(input.text.toString().toBigDecimal() <= BigDecimal(0)){
            input.setError("Value cannot be empty or negative")
            validPrice = false
        }

        return validPrice
    }

}

