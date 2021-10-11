package com.aneke.peter.eyweather.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException

fun Exception.resolveMessage() : String {
    var message = ""
    try {
        if (this is HttpException) {
            if (this.code() == 500){
                message = "Oops! Something went wrong on our servers"
            } else {
                message = JSONObject(this.response()?.errorBody()?.string()).getString("message")
            }
        } else if (this is UnknownHostException) {
            message = "Could not connect to network, please check your internet connection"
        } else {
            message = this.message ?: "The application has encountered an unknown error"
        }
    } catch (e : Exception) {
        message = "The application has encountered an unknown error"
    }
    return message
}

fun Activity.showAlert(title : String = "Error", message : String) {
    val builder = AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK"){ dialog, _ ->
            dialog.dismiss()
        }
    }
    if (!isFinishing) {
        val dialog = builder.create()
        dialog.show()
    }
}
