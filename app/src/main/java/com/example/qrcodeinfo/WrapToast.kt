package com.example.myapplication

import android.content.Context
import android.widget.Toast
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import com.example.qrcodeinfo.R


fun Toast.showCustomToast(message: String, activity: Activity) {
    val layout = activity.layoutInflater.inflate(
        R.layout.custom_toast_layout,
        activity.findViewById(R.id.toast_container)
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.TOP, 0, 40)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}