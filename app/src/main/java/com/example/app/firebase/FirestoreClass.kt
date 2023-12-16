package com.example.app.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.app.RegisterActivity
import com.example.app.User
//import com.example.app.utils.Constants
//import com.example.app.User
import com.example.app.profile
import com.example.app.*
import com.example.app.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo : User){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo,SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()

            }
    }

    fun updateUserProfileData(
        activity: profile,
        userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)// A hashmap of fields which are to be updated
            .addOnSuccessListener {
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")
            }.addOnFailureListener {
                Log.e(activity.javaClass.simpleName, "Error while updating")
            }
    }

    fun loadUserData(activity: Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())//want to get the document that is assigned to the user that is currently logged in
            .get() //getting the user details
            .addOnSuccessListener {document->
                val loggedInUser = document.toObject(User::class.java)!!
                when(activity){
                    is Login -> {
                        activity.signInSuccess(loggedInUser)
                    }
//                    is profile -> {
//                        activity.setUserDataInUI(loggedInUser)
//                    }
                }
            }
    }

    fun getCurrentUserId(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}