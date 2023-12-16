package com.example.app

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.app.firebase.FirestoreClass
import com.example.app.utils.LoadingDialog
import com.example.app.RegisterActivity
import com.example.app.User
import com.example.app.disconnected
import com.example.app.forgotpassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var number =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val window : Window = this@Login.window
        window.statusBarColor = ContextCompat.getColor(this@Login,R.color.backgroundMA)
        window.navigationBarColor = ContextCompat.getColor(this@Login,R.color.backgroundMA)


        // Initialize Firebase Auth
        auth = Firebase.auth

        val et_name: EditText = findViewById(R.id.et_name)
        val et_name2: EditText = findViewById(R.id.et_name2)
        val button: TextView = findViewById(R.id.button)
        val register:TextView = findViewById(R.id.register)
        val textview4:TextView = findViewById(R.id.textView4)

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
        button.setOnClickListener {
            performLogin2()
//            Toast.makeText(this@Login, "bhakkk bc", Toast.LENGTH_SHORT).show()
        }


        textview4.setOnClickListener {
            forgotpasswordperform()
        }
    }

    private fun performLogin2(){

        if (checkForInternet(this)) {
            performLogin()
                /**lets call the loading class*/
            val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run(){
                    if (number == 1){
                        loading.isDismiss()
                    }
                }
            },1000)
        } else {
            val intent = Intent(this, disconnected::class.java)
            startActivity(intent)
        }

    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun forgotpasswordperform(){
        val intent = Intent(this, forgotpassword::class.java)
        startActivity(intent)
    }

    fun signInSuccess(user: User){
        val verification = auth?.currentUser?.isEmailVerified
        if(verification == true){
            val user = auth.currentUser
            val str = user!!.uid
            val intent = Intent(this@Login,MainActivity::class.java)
            intent.putExtra("uID",str)
            startActivity(intent)
            Toast.makeText(this@Login, "Login Success", Toast.LENGTH_SHORT).show()
//            Log.e(TAG, "signInSuccess: hatttt", )
            finish()
        }
        else{
            auth.currentUser?.sendEmailVerification()
                ?.addOnSuccessListener {
                    Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun performLogin() {
        val email:EditText = findViewById(R.id.et_name2)
        val password:EditText = findViewById(R.id.et_name)
        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    number = 1
                    //verify email check
                    FirestoreClass().loadUserData(this)
                    // Sign in success, navigate to Main Activity
                }
                else {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                this.finish()
                Toast.makeText(baseContext, "Authentication failed.  ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}