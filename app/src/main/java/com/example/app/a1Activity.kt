package com.example.app

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import java.io.Serializable
import java.sql.Types.NULL
import java.util.ArrayList


class a1Activity : AppCompatActivity() {

    private lateinit var username : TextView
    private lateinit var password : TextView
    private lateinit var img : ImageView
    private lateinit var notes : TextView
    private lateinit var edit : Button
    private lateinit var Call : Button
//    private lateinit var Cart : Button

    private lateinit var user : EditText
    private lateinit var pass : EditText
    private lateinit var not : EditText
    private lateinit var change : Button
    private lateinit var save : Button
    private lateinit var textView: TextView
    private lateinit var cd : CardView

//    private lateinit var imgList : ArrayList<Int>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a1activity)

        supportActionBar?.title = "Cyclizer"


        fun typeWriterAnimation(textView: TextView, text: String) {
            val stringBuilder = StringBuilder()
            var index = 0

            // Create a new Handler and Runnable
            val handler = Handler()
            val runnable = object : Runnable {
                override fun run() {
                    // Add the next character from the text to the StringBuilder
                    stringBuilder.append(text[index])

                    // Set the TextView text to the contents of the StringBuilder
                    textView.text = stringBuilder.toString()

                    // Increment the index
                    index++

                    // If we haven't reached the end of the text yet, schedule another
                    // iteration of this Runnable
                    if (index < text.length) {
                        handler.postDelayed(this, 50)
                    }
                }
            }

            // Start the Runnable
            handler.postDelayed(runnable, 3000)
        }

        username = findViewById(R.id.textView2)
        password = findViewById(R.id.textView3)
        img = findViewById(R.id.imageView2)
        notes = findViewById(R.id.textView4)
//        edit = findViewById(R.id.button)
        textView = findViewById(R.id.textView7)
        cd = findViewById(R.id.cardView)

        val indx = intent.extras?.getInt("Index",0)
//        val imgList = intent.getIntegerArrayListExtra("names")
        val names = intent.getStringArrayListExtra("naam")
        val emoil = intent.extras?.getString("emoil")
        val possword = intent.extras?.getString("possword")
        val nates = intent.extras?.getString("nates")
        val uri = intent.extras?.getString("pics")
        val uid = intent.extras?.getString("UID")

        val IMAGE_PICKER_REQUEST_CODE = 100

        if(names != null) textView.setText(names[indx!!].toString())


        username.setText("Owner : " + emoil)
        password.setText("Contact : " + possword)
//        notes.setText(nates)
        typeWriterAnimation(notes,nates!!)



//        img.setImageResource(uri!!)
        Glide.with(this@a1Activity).load(uri).into(img)

//        img.setImageURI(uri as Uri)

//        edit.setOnClickListener {
//            val dialog = Dialog(this@aaActivity)
//            dialog.setContentView(R.layout.editting)
//            user = dialog.findViewById(R.id.editTextTextPersonName)
//            pass = dialog.findViewById(R.id.editTextTextPersonName3)
//            not = dialog.findViewById(R.id.editTextTextPersonName4)
//            change = dialog.findViewById(R.id.button8)
//            save = dialog.findViewById(R.id.button7)
//
//            not.setText(notes.text)
//
//            save.setOnClickListener {
//                val s1 = user.text.toString()
//                val s2 = pass.text.toString()
//                val s3 = not.text.toString()
//
//                if(s1 == "" || s2 == "" || s3 == ""){
//                    Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
//                } else{
//                    if(names != null) {
//                        val database = FirebaseDatabase.getInstance()
//                        val rootNode = database.reference.child("Users")
//                        val tmp = rootNode.child(uid!!)
//                        val childNode = tmp.child(names[indx!!])
//
//                        username.setText("Username/E-Mail : " + s1)
//                        password.setText("Password : " + s2)
//                        typeWriterAnimation(notes,s3)
//                        childNode.child("Username or E-Mail").setValue(s1)
//                        childNode.child("Password").setValue(s2)
//                        childNode.child("Notes").setValue(s3)
//                        Toast.makeText(
//                            this,
//                            "Changes saved successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//                dialog.dismiss()
////                overridePendingTransition(R.anim.dialog_exit, R.anim.dialog)
//            }
//            dialog.show()
////            overridePendingTransition(R.anim.dialog, R.anim.dialog_exit)
//        }


        val an = AnimationUtils.loadAnimation(this@a1Activity,R.anim.animation1)
//        cd.startAnimation(an)

        Call = findViewById(R.id.call)

        Call.setOnClickListener {
//            possword!!.replace(possword,"454545")
            if(possword!!.isNotEmpty()) {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$possword")
                startActivity(dialIntent)
            }
        }

//        Cart = findViewById(R.id.cart)
//
//        Cart.setOnClickListener {
//
//        }



    }
}