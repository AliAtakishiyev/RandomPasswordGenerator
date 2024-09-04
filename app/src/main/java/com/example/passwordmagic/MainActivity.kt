package com.example.passwordmagic

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.passwordmagic.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Initialize binding
    private lateinit var binding: ActivityMainBinding

    // define checkbox variables
    private lateinit var cbLowercase: CheckBox
    private lateinit var cbUppercase: CheckBox
    private lateinit var cbNumbers: CheckBox
    private lateinit var cbSymbols: CheckBox



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create a dialog everytime app opens
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.settings_dialog)

        cbLowercase = dialog.findViewById<CheckBox>(R.id.cbLowercase)
        cbUppercase = dialog.findViewById<CheckBox>(R.id.cbUppercase)
        cbNumbers = dialog.findViewById<CheckBox>(R.id.cbNumbers)
        cbSymbols = dialog.findViewById<CheckBox>(R.id.cbSymbols)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // disable dark mode in app


        //generate random password with randomPasswordGenerator() function
        binding.btnGenerate.setOnClickListener {
            randomPasswordGenerator()
        }

        //open settings dialog
        binding.btnSettings.setOnClickListener {

            val btnApply = dialog.findViewById<Button>(R.id.btnApply)


            btnApply.setOnClickListener {
                dialog.dismiss()

                if(!cbLowercase.isChecked && !cbUppercase.isChecked && !cbNumbers.isChecked && !cbSymbols.isChecked){
                    cbLowercase.isChecked = true
                }


            }

            dialog.show()

        }

        //copy text to clipboard
        binding.btnCopy.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("label", binding.tvPassword.text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(this, "${binding.tvPassword.text}", Toast.LENGTH_SHORT).show()
        }



    }

    private fun randomPasswordGenerator(length: Int = 12) {

        val sb = StringBuilder(length)

        val upperCaseLetters = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
        val lowerCaseLetters = arrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
        val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        val specialCharacters = arrayOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '[', ']', '{', '}', '|', ':', ';', ',', '.', '?', '/')

        val final = mutableListOf<Char>() // Use a MutableList to store allowed characters


        if (cbLowercase.isChecked) {
            final.addAll(lowerCaseLetters)
        }
        if (cbUppercase.isChecked) {
            final.addAll(upperCaseLetters)
        }
        if (cbNumbers.isChecked) {
            final.addAll(digits)
        }
        if (cbSymbols.isChecked) {
            final.addAll(specialCharacters)
        }



        for(x in 0 until length){
            val random = (final.indices).random()
            sb.append(final[random])
        }

        binding.tvPassword.text = sb.toString()
        Log.d("TAG", "randomPasswordGenerator: $sb")
    }


}