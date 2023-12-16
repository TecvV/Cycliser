package com.example.app

//import com.example.app.firebase.FirestoreClasstoreClass
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.utils.LoadingDialog
import com.example.app.User
import com.example.app.firebase.FirestoreClass
import com.example.contactarrayrecycleview.CustomAdapter
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException


class ccart : AppCompatActivity() {

    private var mSelectedImageFileUri: Uri? = null
    private var mProfileImageUrl : String = " "
//    private lateinit var uid : String

    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private lateinit var rootNode : FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var recyclerView: RecyclerView;

//    private lateinit var ADD : Button
//    private lateinit var cardView: CardView
//    private lateinit var add_name : EditText
//    private lateinit var floatingActionButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cccart)

        supportActionBar?.title = "My Cart"

//        lateinit var floatingActionButton : Button

//        lateinit var mUserDetails: User
//        FirestoreClass().loadUserData(this@profile)
//
//        val floatingActionButton : FloatingActionButton = findViewById(R.id.floatingActionButton)
//        val log_out_btn: LinearLayout = findViewById(R.id.log_out_btn)
//        val home_btn: LinearLayout = findViewById(R.id.home_btn)
//        val profile_btn: LinearLayout = findViewById(R.id.profile_btn)
//        val iv_profile_user_image: CircleImageView = findViewById(R.id.iv_profile_user_image)
//        val et_name3: TextView = findViewById(R.id.et_name3)
//        val et_name2: TextView = findViewById(R.id.et_name2)
//        val Phone_number: TextView = findViewById(R.id.Phone_number)
//        val window: Window = this@profile.window
//        window.statusBarColor = ContextCompat.getColor(this@profile, R.color.backgroundMA)
//        window.navigationBarColor = ContextCompat.getColor(this@profile, R.color.backgroundMA)
//
//        floatingActionButton.setOnClickListener{
//            if(mSelectedImageFileUri != null){
//                uploadUserImage()
//            }
//
//        }
//
//        log_out_btn.setOnClickListener {
//            val loading = LoadingDialog(this)
//            loading.startLoading()
//            val handler = Handler()
//            handler.postDelayed(object : Runnable {
//                override fun run() {
//                    loading.isDismiss()
//                }
//            }, 3000)
//            FirebaseAuth.getInstance().signOut()
//            finishAffinity()
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
//        }
//
//        iv_profile_user_image.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                showImageChooser()
//            } else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    READ_STORAGE_PERMISSION_CODE
//                )
//            }
//        }
//
//        home_btn.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//
//        }

        val Uid = intent?.extras?.getString("uid")
        val imgList = ArrayList<Uri>();
        val un = ArrayList<String>()
        val pass = ArrayList<String>()
        val nn = ArrayList<String>()
        val E_un = ArrayList<String>()
        val E_pass = ArrayList<String>()
        val E_nn = ArrayList<String>()

        val names = ArrayList<String>()
//        val uid = "Common"
//        val UID = intent.extras?.getString("uID")
//        names.add("G-Mail")
//        names.add("Facebook")
//        names.add("Instagram")
//        names.add("Microsoft Teams")
//        names.add("Codeforces")


//        imgList.add(R.drawable.google);
//        imgList.add(R.drawable.fb);
//        imgList.add(R.drawable.insta);
//        imgList.add(R.drawable.ms)
//        imgList.add(R.drawable.cf)


//        val c1 = ContactArray("G-Mail")
//        val c2 = ContactArray("Facebook")
//        val c3 = ContactArray("Instagram")
//        val c4 = ContactArray("Microsoft Teams")
//        val c5 = ContactArray("Codeforces")

        val contactArray = ArrayList<ContactArray>()
//        contactArray.add(c1)
//        contactArray.add(c2)
//        contactArray.add(c3)
//        contactArray.add(c4)
//        contactArray.add(c5)

        if(Uid == null) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            finish()
        }

        recyclerView = findViewById(R.id.rec)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val customAdapter = CustomAdapter(contactArray,names,imgList)

//        floatingActionButton = findViewById(R.id.button4)

        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("Users1")
        val ref = reference.child(Uid!!)
        val REF = ref.child("MyCart")
        val ref1 = reference.child("Common")

//        FirebaseDatabase.getInstance().setPersistenceEnabled(false);



//        val accountRef = FirebaseDatabase.getInstance().getReference(uid)

        REF.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                names.clear()
                for (accountSnapshot in dataSnapshot.children) {
                    val accountName = accountSnapshot.key // get the name of the account
//                    val uri = accountSnapshot.child("Pic")
//                    imgList.add(uri as Uri)
                    names.add(accountName.toString())
                    // Create a new ContactArray object with the account name and add it to the list
                    val contact = ContactArray(accountName!!)
                    contactArray.add(contact)
                }

//                customAdapter = CustomAdapter(contactArray,names,imgList)
                recyclerView.adapter = customAdapter
                // Do something with the updated contact list, such as updating a ListView
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
//        floatingActionButton = findViewById(R.id.floatingActionButton2)
//        floatingActionButton.setOnClickListener {
//            val dialog = Dialog(this@ccart)
//            dialog.setContentView(R.layout.adding)
//            add_name = dialog.findViewById(R.id.editTextTextPersonName2)
//            cardView = dialog.findViewById(R.id.cd)
//            ADD = dialog.findViewById(R.id.button2)
////            change = dialog.findViewById(R.id.button4)
//            cardView.radius = 12F
//
////            ADD.setOnClickListener {
////                val s = add_name.text
////                if(s.toString() == ""){
////                    Toast.makeText(this, "Please provide the name of Item to be added", Toast.LENGTH_SHORT).show()
////                }
////                else{
////
////
////                    val nms = ref.child(s.toString())
////
////                    val email = nms.child("Owner")
////                    email.setValue("to be added")
////                    val pass = nms.child("Cost")
////                    pass.setValue("to be added")
////                    val notes = nms.child("Desc")
////                    notes.setValue("to be added")
////
////
////                    val nms1 = ref1.child(s.toString())
////
////                    val email1 = nms1.child("Owner")
////                    email1.setValue("to be added")
////                    val pass1 = nms1.child("Cost")
////                    pass1.setValue("to be added")
////                    val notes1 = nms1.child("Desc")
////                    notes1.setValue("to be added")
////
////                    names.add(add_name.text.toString())
////                    imgList.add(R.drawable.plus)
////                    contactArray.add(ContactArray(s.toString()))
////                    customAdapter.notifyItemInserted(contactArray.size-1)
////                    Toast.makeText(this, "Item Posted successfully !!", Toast.LENGTH_SHORT).show()
////                }
////                dialog.dismiss()
////                Toast.makeText(this, "Please make sure to add all the details of the newly Posted Item from your profile section", Toast.LENGTH_SHORT).show()
////                val it = Intent(this@profile, MainActivity::class.java)
////                startActivity(it)
////            }
//
//            ADD.setOnClickListener {
//                val s = add_name.text.toString()
//
//                if (s.isEmpty()) {
//                    Toast.makeText(this, "Please provide the name of Item to be added", Toast.LENGTH_SHORT).show()
//                } else {
//                    ref1.child(s).addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(commonSnapshot: DataSnapshot) {
//                            if (commonSnapshot.exists()) {
//                                // The name already exists in "Common" node, handle accordingly
//                                Toast.makeText(this@ccart, "Item with name $s already exists. Please choose another name for this item", Toast.LENGTH_SHORT).show()
//                            } else {
//                                // The name does not exist in "Common" node, proceed with adding
//                                val nms = REF.child(s)
//
//                                nms.child("Owner").setValue("to be added")
//                                nms.child("Cost").setValue("to be added")
//                                nms.child("Desc").setValue("to be added")
//
//                                val nms1 = ref1.child(s)
//
//                                nms1.child("Owner").setValue("to be added")
//                                nms1.child("Cost").setValue("to be added")
//                                nms1.child("Desc").setValue("to be added")
//
//                                names.add(s)
//                                imgList.add(R.drawable.plus)
//                                contactArray.add(ContactArray(s))
//                                customAdapter.notifyItemInserted(contactArray.size - 1)
//                                Toast.makeText(this@ccart, "Item with name $s posted successfully!", Toast.LENGTH_SHORT).show()
//
//                                dialog.dismiss()
//
//                                // Additional message
//                                Toast.makeText(
//                                    this@ccart,
//                                    "Please make sure to add all the details of the newly Posted Item from your profile section",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//
//                                val intent = Intent(this@ccart, MainActivity::class.java)
//                                startActivity(intent)
//                            }
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            // Handle errors here
//                            Toast.makeText(this@ccart, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                }
//            }
//
//
////            change.setOnClickListener {
////                Toast.makeText(this, "Your icon will be changed soon !!", Toast.LENGTH_SHORT).show()
////            }
//
//
//            dialog.show()
////            imgList.add(R.drawable.plus)
////            names.add("New Account")
////            contactArray.add(ContactArray("New acc",R.drawable.plus))
////            Toast.makeText(this,"SIZE IS : " + contactArray.size, Toast.LENGTH_SHORT).show()
////            customAdapter.notifyItemInserted(contactArray.size-1)
//        }
        customAdapter.setOnClickListener(object : CustomAdapter.OnClickListener{
            override fun onItemClick(position: Int) {

                // Handle the click event for the item at position
                var intent = Intent(this@ccart, a1Activity::class.java)
//                Toast.makeText(this@profile, Uid, Toast.LENGTH_SHORT).show()


                REF.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        if(snapshot.key == names[position]){
                            val emoil = snapshot.child("Owner").value.toString()
                            val possword = snapshot.child("Cost").value.toString()
                            val nates = snapshot.child("Desc").value.toString()
                            val pics = snapshot.child("Pic").value.toString()

                            // Do something with the retrieved data
                            // For example, you could store it in three separate strings
//                        val email = emailOrUsername // or do some processing to extract the email
//                        val username = emailOrUsername // or do some processing to extract the username

//                        Log.d(TAG, "Email/Username: $emailOrUsername")
//                        Log.d(TAG, "Password: $password")
//                        Log.d(TAG, "Notes: $notes")

                            intent.putExtra("Index",position)
                            intent.putExtra("pics", pics)
//                      intent.putIntegerArrayListExtra("names",imgList)
                            intent.putStringArrayListExtra("naam",names)
                            intent.putExtra("emoil",emoil)
                            intent.putExtra("possword",possword)
                            intent.putExtra("nates",nates)

                            intent.putExtra("UID",Uid)
//                      intent.putStringArrayListExtra("un",un)
//                      intent.putStringArrayListExtra("pass",pass)
//                      intent.putStringArrayListExtra("nn",nn)
//                      intent.putStringArrayListExtra("E_un",E_un)
//                      intent.putStringArrayListExtra("E_pass",E_pass)
//                      intent.putStringArrayListExtra("E_nn",E_nn)
                            startActivity(intent)
                        }
                    }

                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                        // Handle changes to the data, if necessary
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        // Handle removal of data, if necessary
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                        // Handle changes to the order of the data, if necessary
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
//                        Log.w(TAG, "Database error: ${databaseError.toException()}")
                    }


                })



            }
        })

        customAdapter.setOnLongClickListener(object : CustomAdapter.OnLongClickListener {
            override fun onItemLongClick(position: Int) {
                val alertDialogBuilder = AlertDialog.Builder(this@ccart)
                alertDialogBuilder.setTitle("Delete Post")
                alertDialogBuilder.setMessage("Are you sure you want to delete your " + names[position] + " Post?")
                alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->

                    if(position >= 0 && position < names.size){
                        REF.child(names[position]).removeValue()
//                        ref1.child(names[position]).removeValue()

                        // Remove the item from the local storage
//                        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//                        val editor = sharedPrefs.edit()
//                        editor.remove("name${position}")
//                        editor.remove("email${position}")
//                        editor.remove("password${position}")
//                        editor.remove("notes${position}")
//                        editor.apply()

                        imgList.removeAt(position)
                        names.removeAt(position)
                        contactArray.removeAt(position)
                        customAdapter.notifyItemRemoved(position)

                        Toast.makeText(this@ccart, "Item Deleted from Cart successfully !!", Toast.LENGTH_SHORT).show()

                        val it = Intent(this@ccart, MainActivity::class.java)
                        startActivity(it)

                    }

                }

                alertDialogBuilder.setNegativeButton("No") { dialog, which ->
//                    val it = Intent(this@profile, MainActivity::class.java)
//                    startActivity(it)
                }
                alertDialogBuilder.show()
            }
        })



    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        val profiletoimg: CircleImageView = findViewById(R.id.iv_profile_user_image)
//        val et_name3: TextView = findViewById(R.id.et_name3)
//        val et_name2: TextView = findViewById(R.id.et_name2)
//        val Phone_number: TextView = findViewById(R.id.Phone_number)
//        if (resultCode == Activity.RESULT_OK
//            && requestCode == PICK_IMAGE_REQUEST_CODE
//            && data!!.data != null
//        ) {
//            // The uri of selection image from phone storage.
//            mSelectedImageFileUri = data.data!!
//            //We used try catch because we are uploading something so there could be error while uploading
//            try {
//                Glide
//                    .with(this@profile)
//                    .load(mSelectedImageFileUri)
//                    .centerCrop()
//                    .placeholder(R.drawable.personholder)
//                    .into(profiletoimg); // the view in which the image will be loaded.
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
//            if (grantResults.isNotEmpty()
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED
//            ) {
//                showImageChooser()
//            }
//        } else {
//            Toast.makeText(
//                this, "Oops you just rejected the permission of external storage",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    private fun showImageChooser() {
//        var galleryIntent = Intent(
//            Intent.ACTION_PICK,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        )
//
//        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
//
//    }
//
//    fun setUserDataInUI(user: User) {
//
//        val profiletoimg: CircleImageView = findViewById(R.id.iv_profile_user_image)
//        val et_name3: TextView = findViewById(R.id.et_name3)
//        val et_name2: TextView = findViewById(R.id.et_name2)
//        val Phone_number: TextView = findViewById(R.id.Phone_number)
//
//        Glide
//            .with(this@profile)
//            .load(user.image)
//            .centerCrop()
//            .placeholder(R.drawable.personholder)
//            .into(profiletoimg);
//
//        et_name3.setText(user.name)
//        et_name2.setText(user.email)
//        if (user.mobile != 0L) {
//            Phone_number.setText(user.mobile.toString())
//        }
//    }
//
//    @SuppressLint("SuspiciousIndentation")
//    private fun uploadUserImage(){
//        if(mSelectedImageFileUri != null){
//            val sRef : StorageReference =
//                FirebaseStorage.getInstance().reference.child(
//                    "USER_IMAGE" + System.currentTimeMillis()
//                        + "." + getFileExtension(mSelectedImageFileUri!!))
//
//                sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener {
//                    taskSnapshot ->
//                    Log.i(
//                        "Firebase Image URL",
//                        taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
//                    )
//                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
//                        uri ->
//                        Log.i("Downloaded Image URL", uri.toString())
//                        mProfileImageUrl = uri.toString()
//                    }
//                }.addOnFailureListener{
//                           exception ->
//                            Toast.makeText(this@profile,
//                            exception.message,
//                            Toast.LENGTH_LONG
//                            ).show()
//                }
//
//        }
//    }
//
//    private fun getFileExtension(uri: Uri): String?{
//        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri!!))
//    }
}


