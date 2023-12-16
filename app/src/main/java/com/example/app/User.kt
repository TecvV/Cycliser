package com.example.app

import android.media.Image
import android.os.Parcel
import android.os.Parcelable

data class User(val id : String = "",
                val name : String = "",
                val email: String = "",
                val image: String = "",
                val mobile: Long = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) = with(dest) {
       this!!.writeString(id)
        writeString(name)
        writeString(email)
        writeString(image)
        writeLong(mobile)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
