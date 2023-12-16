package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.app.utils.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.EditText

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.contactarrayrecycleview.CustomAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.core.view.View
import kotlinx.coroutines.newFixedThreadPoolContext
import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.collections.ArrayList

class start : AppCompatActivity() {

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
    private lateinit var floatingActionButton: FloatingActionButton

    private lateinit var rootNode : FirebaseDatabase
    private lateinit var reference: DatabaseReference


    private lateinit var emoil : String
    private lateinit var possword : String
    private lateinit var nates : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)

//        val imgList = ArrayList<Int>();
//        val un = ArrayList<String>()
//        val pass = ArrayList<String>()
//        val nn = ArrayList<String>()
//        val E_un = ArrayList<String>()
//        val E_pass = ArrayList<String>()
//        val E_nn = ArrayList<String>()
//
//        val names = ArrayList<String>()
//
//        imgList.add(R.drawable.sample);
////        imgList.add(R.drawable.sample);
////        imgList.add(R.drawable.sample);
////        imgList.add(R.drawable.sample)
////        imgList.add(R.drawable.sample)
//
//        val uid = intent.getStringExtra("uID").toString()
//
//        val contactArray = ArrayList<ContactArray>()
//        recyclerView = findViewById(R.id.recycle)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        val customAdapter = CustomAdapter(contactArray)
////
//        rootNode = FirebaseDatabase.getInstance()
//        reference = rootNode.getReference("Users")
//        val ref = reference.child(uid)
//
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                names.clear()
//                for (accountSnapshot in dataSnapshot.children) {
//                    val accountName = accountSnapshot.key // get the name of the account
//
//                    names.add(accountName.toString())
//
//                    // Create a new ContactArray object with the account name and add it to the list
//                    val contact = ContactArray(accountName!!)
//                    contactArray.add(contact)
//                }
//
////                customAdapter = CustomAdapter(contactArray)
//                recyclerView.adapter = customAdapter
//                // Do something with the updated contact list, such as updating a ListView
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
////
////
//////        val c1 = ContactArray("G-Mail")
//////        val c2 = ContactArray("Facebook")
//////        val c3 = ContactArray("Instagram")
//////        val c4 = ContactArray("Microsoft Teams")
//////        val c5 = ContactArray("Codeforces")
//////
//////        contactArray.add(c1)
//////        contactArray.add(c2)
//////        contactArray.add(c3)
//////        contactArray.add(c4)
//////        contactArray.add(c5)
//////
//////        recyclerView.adapter = customAdapter
////
////
////
////
//        floatingActionButton = findViewById(R.id.floatingActionButton2)
//        floatingActionButton.setOnClickListener {
//            val dialog = Dialog(this@Afterlogin)
//            dialog.setContentView(R.layout.adding)
//            add_name = dialog.findViewById(R.id.editTextTextPersonName2)
//            cardView = dialog.findViewById(R.id.cd)
//            ADD = dialog.findViewById(R.id.button2)
//
//            cardView.radius = 12F
////            Toast.makeText(this, uid + " " + names.size, Toast.LENGTH_SHORT).show()
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
//                    val email = nms.child("Username or E-Mail")
//                    email.setValue("to be added")
//                    val pass = nms.child("Password")
//                    pass.setValue("to be added")
//                    val notes = nms.child("Notes")
//                    notes.setValue("to be added")
//
//                    names.add(add_name.text.toString())
//                    imgList.add(R.drawable.sample)
//                    contactArray.add(ContactArray(s.toString()))
//                    customAdapter.notifyItemInserted(contactArray.size-1)
//                    Toast.makeText(this, "New Account added Successfully", Toast.LENGTH_SHORT).show()
//                }
//                dialog.dismiss()
//            }
////
////
////
//            dialog.show()
////            Toast.makeText(this, uid + " " + names.size, Toast.LENGTH_SHORT).show()
//        }
////
////
////
//        customAdapter.setOnClickListener(object : CustomAdapter.OnClickListener{
//            override fun onItemClick(position: Int) {
//
//                // Handle the click event for the item at position
//                var intent = Intent(this@Afterlogin, aaActivity::class.java)
//
//
//
//                ref.addChildEventListener(object : ChildEventListener {
//                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                        if(snapshot.key == names[position]){
//                            val emoil = snapshot.child("Username or E-Mail").value.toString()
//                            val possword = snapshot.child("Password").value.toString()
//                            val nates = snapshot.child("Notes").value.toString()
//
//                            // Do something with the retrieved data
//                            // For example, you could store it in three separate strings
////                        val email = emailOrUsername // or do some processing to extract the email
////                        val username = emailOrUsername // or do some processing to extract the username
//
////                        Log.d(TAG, "Email/Username: $emailOrUsername")
////                        Log.d(TAG, "Password: $password")
////                        Log.d(TAG, "Notes: $notes")
//
//                            intent.putExtra("Index",position)
////                      intent.putIntegerArrayListExtra("names",imgList)
//                            intent.putStringArrayListExtra("naam",names)
//                            intent.putExtra("emoil",emoil)
//                            intent.putExtra("possword",possword)
//                            intent.putExtra("nates",nates)
//
//                            intent.putExtra("UID",uid)
////                      intent.putStringArrayListExtra("un",un)
////                      intent.putStringArrayListExtra("pass",pass)
////                      intent.putStringArrayListExtra("nn",nn)
////                      intent.putStringArrayListExtra("E_un",E_un)
////                      intent.putStringArrayListExtra("E_pass",E_pass)
////                      intent.putStringArrayListExtra("E_nn",E_nn)
//                            startActivity(intent)
//                        }
//                    }
//
//                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                        // Handle changes to the data, if necessary
//                    }
//
//                    override fun onChildRemoved(snapshot: DataSnapshot) {
//                        // Handle removal of data, if necessary
//                    }
//
//                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                        // Handle changes to the order of the data, if necessary
//                    }
//
//                    override fun onCancelled(databaseError: DatabaseError) {
////                        Log.w(TAG, "Database error: ${databaseError.toException()}")
//                    }
//
//
//                })
//
//
//
//            }
//        })
//
//        customAdapter.setOnLongClickListener(object : CustomAdapter.OnLongClickListener{
//            override fun onItemLongClick(position: Int) {
//                val alertDialogBuilder = AlertDialog.Builder(this@Afterlogin)
//                alertDialogBuilder.setTitle("Delete Account")
//                alertDialogBuilder.setMessage("Are you sure you want to delete your " + names[position] + " Account?")
//                alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
//
//                    if(position >= 0 && position < names.size){
//                        ref.child(names[position]).removeValue()
//
//                        // Remove the item from the local storage
//                        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//                        val editor = sharedPrefs.edit()
//                        editor.remove("name${position}")
//                        editor.remove("email${position}")
//                        editor.remove("password${position}")
//                        editor.remove("notes${position}")
//                        editor.apply()
//
//                        imgList.removeAt(position)
//                        names.removeAt(position)
//                        contactArray.removeAt(position)
//                        customAdapter.notifyItemRemoved(position)
//
//                    }
//
//                }
//
//                alertDialogBuilder.setNegativeButton("No") { dialog, which ->
//
//                }
//                alertDialogBuilder.show()
//            }
//        })

        val log_out_btn : LinearLayout = findViewById(R.id.log_out_btn)
        val profile_btn: LinearLayout = findViewById(R.id.profile_btn)
        val window : Window = this@start.window
        window.statusBarColor = ContextCompat.getColor(this@start,R.color.backgroundMA)
        window.navigationBarColor = ContextCompat.getColor(this@start,R.color.backgroundMA)

        log_out_btn.setOnClickListener{
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

        profile_btn.setOnClickListener{
            val intent = Intent(this,profile::class.java)
            startActivity(intent)
        }

    }
}