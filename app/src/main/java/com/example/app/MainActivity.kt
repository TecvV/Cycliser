package com.example.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.utils.LoadingDialog
import com.example.contactarrayrecycleview.CustomAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
//import com.squareup.okhttp.internal.DiskLruCache.Snapshot
import kotlinx.coroutines.newFixedThreadPoolContext
import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var next: Button;
    private lateinit var prev: Button;
    private lateinit var edit: Button;
    private lateinit var add: Button;
    private lateinit var display: TextView;
    private lateinit var recyclerView: RecyclerView;
    private lateinit var add_name : EditText
    private lateinit var ADD : Button
    private lateinit var change : Button
    private lateinit var cardView: CardView
//    private lateinit var searchView: SearchView
//    private lateinit var floatingActionButton: FloatingActionButton

    private lateinit var rootNode : FirebaseDatabase
    private lateinit var reference: DatabaseReference


//    private lateinit var emoil : String
//    private lateinit var possword : String
//    private lateinit var nates : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgList = ArrayList<Uri>();
        val un = ArrayList<String>()
        val pass = ArrayList<String>()
        val nn = ArrayList<String>()
        val E_un = ArrayList<String>()
        val E_pass = ArrayList<String>()
        val E_nn = ArrayList<String>()

        val names = ArrayList<String>()
//        val uid = "Common"
        val UID = intent.extras?.getString("uID")

        val firestore = FirebaseFirestore.getInstance()
        val reff = firestore.collection("users")
        var naame = ""

        reff.document(UID!!)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Document exists, fetch the "name" parameter
                    val name = documentSnapshot.getString("name")
                    if (name != null) {
                        // Use the name as needed
                        naame = naame.plus(name) // Assign the concatenated value back to naame
                        runOnUiThread {
                            supportActionBar?.title = "Welcome $naame"
                        }
                    } else {
                        println("Name is null")
                    }
                } else {
                    println("Document does not exist for UID: $UID")
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
                println("Error getting document: $exception")
            }

//        supportActionBar?.title = naame

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

        recyclerView = findViewById(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val customAdapter = CustomAdapter(contactArray,names,imgList)

        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("Users1")
        val ref = reference.child("Common")

        val ref1 = reference.child(UID!!)
        val REF = ref1.child("MyCart")

//        FirebaseDatabase.getInstance().setPersistenceEnabled(false);

//        val accountRef = FirebaseDatabase.getInstance().getReference(uid)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
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



//        recyclerView = findViewById(R.id.recycle)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        val customAdapter = CustomAdapter(contactArray,names,imgList)
//        recyclerView.adapter = customAdapter


//        floatingActionButton = findViewById(R.id.floatingActionButton2)
//        floatingActionButton.setOnClickListener {
//            val dialog = Dialog(this@MainActivity)
//            dialog.setContentView(R.layout.adding)
//            add_name = dialog.findViewById(R.id.editTextTextPersonName2)
//            cardView = dialog.findViewById(R.id.cd)
//            ADD = dialog.findViewById(R.id.button2)
////            change = dialog.findViewById(R.id.button4)
//            cardView.radius = 12F
//
//            ADD.setOnClickListener {
//                val s = add_name.text
//                if(s.toString() == ""){
//                    Toast.makeText(this, "Please provide the name of Account to be added", Toast.LENGTH_SHORT).show()
//                }
//                else{
//
//
//                    val nms = ref.child(s.toString())
//
//                    val email = nms.child("Owner")
//                    email.setValue("to be added")
//                    val pass = nms.child("Contact")
//                    pass.setValue("to be added")
//                    val notes = nms.child("Desc")
//                    notes.setValue("to be added")
//
//                    names.add(add_name.text.toString())
//                    imgList.add(R.drawable.plus)
//                    contactArray.add(ContactArray(s.toString()))
//                    customAdapter.notifyItemInserted(contactArray.size-1)
//                    Toast.makeText(this, "Item Posted successfully !!", Toast.LENGTH_SHORT).show()
//                }
//                dialog.dismiss()
//            }
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

//

//        val databaseReference = FirebaseDatabase.getInstance().reference.child(uid)

        customAdapter.setOnClickListener(object : CustomAdapter.OnClickListener{
            override fun onItemClick(position: Int) {

                // Handle the click event for the item at position
                var intent = Intent(this@MainActivity, a1Activity::class.java)

                ref.addChildEventListener(object : ChildEventListener {
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

                            intent.putExtra("UID","Common")
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
            @SuppressLint("SuspiciousIndentation")
            override fun onItemLongClick(position: Int) {
                val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setTitle("Add to Cart?")
                alertDialogBuilder.setMessage("Are you sure you want to add " + names[position] + " to your Cart?")
                alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->

                    val itemName = names[position]

                    REF.child(itemName).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(commonSnapshot: DataSnapshot) {
                            if (commonSnapshot.exists()) {
                                // Item already present in the Cart
                                Toast.makeText(this@MainActivity, "Item Already present in Cart", Toast.LENGTH_SHORT).show()
                            } else {
                                // The item does not exist in the "Common" node, proceed with adding
                                val nms = REF.child(itemName)

                                // Use a ValueEventListener to get the values
                                ref.child(itemName).addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(itemSnapshot: DataSnapshot) {
                                        val ss1 = itemSnapshot.child("Owner").value.toString()
                                        val ss2 = itemSnapshot.child("Cost").value.toString()
                                        val ss3 = itemSnapshot.child("Desc").value.toString()
                                        val ss4 = itemSnapshot.child("Pic").value.toString()

                                        nms.child("Owner").setValue(ss1)
                                        nms.child("Cost").setValue(ss2)
                                        nms.child("Desc").setValue(ss3)
                                        nms.child("Pic").setValue(ss4)

                                        // Notify the CartActivity about the change
                                        // You might want to use an Intent to send data to the next activity if needed

                                        Toast.makeText(this@MainActivity, "${itemName} added to your Cart successfully", Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // Handle errors here
                                        Toast.makeText(this@MainActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle errors here
                            Toast.makeText(this@MainActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                alertDialogBuilder.setNegativeButton("No") { dialog, which ->
//                    Toast.makeText(this@MainActivity, "name is : " + naame, Toast.LENGTH_SHORT).show()
                }
                alertDialogBuilder.show()
            }
        })




        val log_out_btn : LinearLayout = findViewById(R.id.log_out_btn)
        val profile_btn: LinearLayout = findViewById(R.id.profile_btn)
        val setting : LinearLayout = findViewById(R.id.settings_btn)
        val window : Window = this@MainActivity.window
        window.statusBarColor = ContextCompat.getColor(this@MainActivity,R.color.backgroundMA)
        window.navigationBarColor = ContextCompat.getColor(this@MainActivity,R.color.backgroundMA)

        log_out_btn.setOnClickListener{
//            val loading = LoadingDialog(this)
//            loading.startLoading()
//            val handler = Handler()
//            handler.postDelayed(object :Runnable{
//                override fun run(){
//                    loading.isDismiss()
//                }
//            },3000)
//            FirebaseAuth.getInstance().signOut()
//            finish()
//            val intent = Intent(this,Login::class.java)
//            startActivity(intent)
//            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show()


            val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
            alertDialogBuilder.setTitle("Logout")
            alertDialogBuilder.setMessage("Are you sure you want to Logout ? ")
            alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
                val loading = LoadingDialog(this)
                loading.startLoading()
                val handler = Handler()
                handler.postDelayed(object :Runnable{
                    override fun run(){
                        loading.isDismiss()
                    }
                },3000)
                FirebaseAuth.getInstance().signOut()
                finish()
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show()


            }

            alertDialogBuilder.setNegativeButton("No") { dialog, which ->

            }
            alertDialogBuilder.show()
        }

        profile_btn.setOnClickListener{
//            Toast.makeText(this@MainActivity, UID, Toast.LENGTH_SHORT).show()
            val intent = Intent(this,profile::class.java)
            intent.putExtra("uid", UID)
            startActivity(intent)
        }

        setting.setOnClickListener {
            val it = Intent(this@MainActivity, ccart::class.java)
            it.putExtra("uid", UID)
            startActivity(it)
        }

    }

}