package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class forgotpassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)


        val window: Window = this@forgotpassword.window
        window.statusBarColor = ContextCompat.getColor(this@forgotpassword, R.color.backgroundMA)
        window.navigationBarColor = ContextCompat.getColor(this@forgotpassword, R.color.backgroundMA)

        val Email: EditText = findViewById(R.id.Enter_email)
        val Reset: TextView = findViewById(R.id.Reset)

        auth = FirebaseAuth.getInstance()


        Reset.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(Email.text.toString()).matches()) {
                Toast.makeText(
                    this@forgotpassword,
                    "Please Re-Check your email",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Firebase.auth.sendPasswordResetEmail(Email.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Check your mail", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,Login::class.java)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@forgotpassword,
                        "Please try-again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        }
    }