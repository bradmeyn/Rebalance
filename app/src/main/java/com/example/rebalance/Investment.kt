package com.example.rebalance

import android.os.Parcelable
import java.io.Serializable

data class Investment(val name:String, val code:String, val price:Float): Serializable
{

}
