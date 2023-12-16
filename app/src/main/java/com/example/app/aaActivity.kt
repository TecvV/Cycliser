package com.example.app

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
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
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.gms.cast.framework.media.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.internal.StorageReferenceUri
import java.io.File
import java.io.IOException
import java.io.Serializable
import java.sql.Types.NULL
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern
import kotlin.math.asin


class aaActivity : AppCompatActivity() {

    private lateinit var username : TextView
    private lateinit var password : TextView
    private lateinit var img : ImageView
    private lateinit var notes : TextView
    private lateinit var edit : Button

    private lateinit var user : EditText
    private lateinit var pass : EditText
    private lateinit var not : EditText
    private lateinit var change : Button
    private lateinit var save : Button
    private lateinit var textView: TextView
    private lateinit var cd : CardView
    private lateinit var Camera : Button
    private lateinit var Gallery : Button
    private lateinit var pro : ImageView

    private var currentPhotoPath: String? = null

    private lateinit var rootNode : FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var StorageReference : StorageReference

//    private lateinit var imgList : ArrayList<Int>

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1
        const val CAPTURE_IMAGE_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aa)

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
        edit = findViewById(R.id.button)
        textView = findViewById(R.id.textView7)
        cd = findViewById(R.id.cardView)

        val indx = intent.extras?.getInt("Index",0)
//        val imgList = intent.getIntegerArrayListExtra("names")
        val names = intent.getStringArrayListExtra("naam")
        val emoil = intent.extras?.getString("emoil")
        val possword = intent.extras?.getString("possword")
        val nates = intent.extras?.getString("nates")
        val uid = intent.extras?.getString("UID")
        val uri = intent.extras?.getString("pics")

//        uid.replace(uid, temp!!)

        val IMAGE_PICKER_REQUEST_CODE = 100

        if(names != null) textView.setText(names[indx!!].toString())


        username.setText("Owner : " + emoil)
        password.setText("Contact : " + possword)
//        notes.setText(nates)
        typeWriterAnimation(notes,nates!!)



//            img.setImageURI(uri as Uri)

        Glide.with(this@aaActivity).load(uri).into(img)

        edit.setOnClickListener {
            val dialog = Dialog(this@aaActivity)
            dialog.setContentView(R.layout.editting)
            user = dialog.findViewById(R.id.editTextTextPersonName)
            pass = dialog.findViewById(R.id.editTextTextPersonName3)
            not = dialog.findViewById(R.id.editTextTextPersonName4)
            change = dialog.findViewById(R.id.button8)
            save = dialog.findViewById(R.id.button7)

            not.setText(notes.text)

            save.setOnClickListener {
                val s1 = user.text.toString()
                val s2 = pass.text.toString()
                val s3 = not.text.toString()
                val numericRegex = "^[0-9]+$"

                if(s1 == "" || s2 == "" || s3 == ""){
                    Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                }
                else if(!Pattern.matches(numericRegex, s2)) {
                    Toast.makeText(this@aaActivity, "Please enter a Valid Contact no. ", Toast.LENGTH_SHORT).show()
                }
                else {
                    if(names != null) {
                        val database = FirebaseDatabase.getInstance()
                        val rootNode = database.reference.child("Users1")
                        val tmp = rootNode.child("Common")

                        val childNode = tmp.child(names[indx!!])
                        username.setText("Owner : " + s1)
                        password.setText("Contact : " + s2)
                        typeWriterAnimation(notes,s3)
                        childNode.child("Owner").setValue(s1)
                        childNode.child("Cost").setValue(s2)
                        childNode.child("Desc").setValue(s3)
//
                        val tmp2 = rootNode.child(uid!!)
                        val TMP = tmp2.child("MyPosts")
                        val ch = TMP.child(names[indx!!])
                        ch.child("Owner").setValue(s1)
                        ch.child("Cost").setValue(s2)
                        ch.child("Desc").setValue(s3)

//                        val tmp3 = rootNode.child("v9bqf0eWFUPr3faRw4YdOskVaCl2")
//                        val TMP1 = tmp3.child("MyCart")
//                        val chld = TMP1.child(names[indx])
//                        chld.child("Owner").setValue(s1)
//                        chld.child("Cost").setValue(s2)
//                        chld.child("Desc").setValue(s3)

                        val arr = mutableListOf<String>()

                        rootNode.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (childSnapshot in dataSnapshot.children) {
                                    arr.add(childSnapshot.key!!)
                                }
                                for(it in arr) {
                                    if(it != "Common") {
//                                        Toast.makeText(this@aaActivity, it, Toast.LENGTH_SHORT).show()
                                        val chld = rootNode.child(it)
                                        val chld1 = chld.child("MyCart")
                                        chld1.child(names[indx]).addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(commonSnapshot: DataSnapshot) {
                                                if (commonSnapshot.exists()) {
                                                    val chld2 = chld1.child(names[indx])
                                                    chld2.child("Owner").setValue(s1)
                                                    chld2.child("Cost").setValue(s2)
                                                    chld2.child("Desc").setValue(s3)
                                                }
                                            }

                                            override fun onCancelled(databaseError: DatabaseError) {
                                                // Handle errors here
                                                Toast.makeText(this@aaActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        })

                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle errors
                                Log.e("FirebaseData", "Error: ${databaseError.message}")
                            }
                        })




//                        val it1 = Intent(this@aaActivity, MainActivity::class.java)
//                        startActivity(it1)



//                        reference = rootNode.getReference("Users1")
//                        val ref = reference.child("Common")
//                        val owner = ref.child("Owner")
//                        owner.setValue(s1);
//                        val cost = ref.child("Cost (in Rs)")
//                        cost.setValue(s2)
//                        val desc = ref.child("Desc")
//                        desc.setValue(s3)
                        Toast.makeText(
                            this,
                            "Item Posted successfully !!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                dialog.dismiss()

//                overridePendingTransition(R.anim.dialog_exit, R.anim.dialog)
            }

            change.setOnClickListener {
//                val dg = Dialog(this@aaActivity)
//                dg.setContentView(R.layout.pic)
//                Camera = dg.findViewById(R.id.camera)
//                Gallery = dg.findViewById(R.id.gallery)

//                Camera.setOnClickListener {
////                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////                    startActivityForResult(intent, 123)
//                    captureImageFromCamera()
//                    dg.dismiss()
//                }

//                Gallery.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, 456)
//                    dg.dismiss()
//                }
//                dg.show()
//                dialog.dismiss()
            }

            dialog.show()
//            overridePendingTransition(R.anim.dialog, R.anim.dialog_exit)
        }


        val an = AnimationUtils.loadAnimation(this@aaActivity,R.anim.animation1)
//        cd.startAnimation(an)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        pro = findViewById(R.id.imageView2)
        val uid = intent.extras?.getString("UID")
        val names = intent.getStringArrayListExtra("naam")
        val indx = intent.extras?.getInt("Index",0)

        val database = FirebaseDatabase.getInstance()
        val rootNode = database.reference.child("Users1")
        val tmp1 = rootNode.child("Common")
        val tmp2 = rootNode.child(uid!!)
        val chldNode = tmp1.child(names?.get(indx!!)!!)
        val tmp3 = tmp2.child("MyPosts")
        val tmp4 = tmp3.child(names?.get(indx!!)!!)

        if(requestCode == 456) {
            val uri = data?.data

            uploadToFireBase(uri!!)
            pro.setImageURI(uri)
        } else {
            val uri = Uri.parse(currentPhotoPath)

            uploadToFireBase(uri!!)
            pro.setImageURI(uri)
        }
    }
    // Inside aaActivity class


// ...

    private fun uploadToFireBase(uri: Uri) {
        val uid = intent.extras?.getString("UID")
        val names = intent.getStringArrayListExtra("naam")
        val indx = intent.extras?.getInt("Index",0)
        val itemName = names?.get(indx!!)

        if (uri != null && uid != null) {
            StorageReference = FirebaseStorage.getInstance().reference

            // Storage path for MyPosts
            val storagePathPosts = "$uid/MyPosts/$itemName/Pic"
            // Storage path for MyCart
            val storagePathCart = "$uid/MyCart/$itemName/Pic"

            // Upload to MyPosts
            uploadImageToStorage(uri, storagePathPosts, uid, itemName!!)

            // Upload to MyCart
//            uploadImageToStorage(uri, storagePathCart, uid, itemName)
        }


    }

    private fun uploadImageToStorage(uri: Uri, storagePath: String, uid : String, itemName : String) {
        val imageRef = StorageReference.child(storagePath)
        imageRef.putFile(uri)
            .addOnSuccessListener {
                // Get the download URL and update the imageURL in the database
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    updateImageUrlInDatabase(downloadUri.toString(), storagePath, uid, itemName)
                    Toast.makeText(this@aaActivity, "Image Uploaded successfully !!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Handle unsuccessful uploads
                Toast.makeText(this@aaActivity, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateImageUrlInDatabase(imageUrl: String, databasePath: String, uid : String, itemName: String) {
        val names = intent.getStringArrayListExtra("naam")
        val indx = intent.extras?.getInt("Index",0)

        val rootNode = FirebaseDatabase.getInstance().reference
        val rootNode1 = rootNode.child("Users1")

        val k1 = rootNode.child("Users1")
        val nodeRef = k1.child(databasePath)
        nodeRef.setValue(imageUrl)

        val chld = k1.child("Common")
        val chld2 = chld.child(itemName)
        val chld3 = chld2.child("Pic")
        chld3.setValue(imageUrl)

        val arr = mutableListOf<String>()

        rootNode1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    arr.add(childSnapshot.key!!)
                }
                for(it in arr) {
                    if(it != "Common") {
                        val chld = rootNode1.child(it)
                        val chld1 = chld.child("MyCart")
                        chld1.child(names!![indx!!]).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(commonSnapshot: DataSnapshot) {
                                if (commonSnapshot.exists()) {
                                    val chld2 = chld1.child(names[indx])
                                    chld2.child("Pic").setValue(imageUrl)
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle errors here
                                Toast.makeText(this@aaActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                            }
                        })

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("FirebaseData", "Error: ${databaseError.message}")
            }
        })

    }

    private fun captureImageFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            // Handle the error
            null
        }

        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.app.fileprovider",
                it
            )
            currentPhotoPath = it.absolutePath
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST_CODE)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

}