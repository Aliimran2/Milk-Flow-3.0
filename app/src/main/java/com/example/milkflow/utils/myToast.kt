package com.example.milkflow.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.milkflow.databinding.ToastLayoutBinding

fun myToast(context: Context, text:String, img: Int){
    val inflater = LayoutInflater.from(context)
    val binding = ToastLayoutBinding.inflate(inflater)


    binding.toastIcon.setImageResource(img)
    binding.toastText.text = text

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_LONG
    toast.view = binding.root
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
    toast.show()
}