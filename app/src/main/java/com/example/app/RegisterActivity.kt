package com.example.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.app.User
//import com.example.app.firebase.FirestoreClass
import com.example.app.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var imageuri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val loginText :TextView = findViewById(R.id.logintext)
        loginText.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val sign_up: TextView = findViewById(R.id.sign_up)
        sign_up.setOnClickListener {
            performsignup()
        }

    }

    fun userRegisteredSuccess(){
        Toast.makeText(this,"you have" + "successfully registered" , Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }


    private fun performsignup() {
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val Name = findViewById<EditText>(R.id.name)



        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val inputEmail = email.text.toString()
        val inputpassword =password.text.toString()
        val name = Name.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputpassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser : FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(firebaseUser.uid,name,registeredEmail)
                    FirestoreClass().registerUser(this,user)
                    finish()
                    // Sign in success, update UI with the signed-in user's information



                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Success",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error Occured  ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}