package com.example.contactarrayrecycleview

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getMainExecutor
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.ContactArray
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.aaActivity
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.cancelFutureOnCompletion
import android.content.Context



class CustomAdapter(private val c: ArrayList<ContactArray>,private val names : ArrayList<String>,private val imgList : ArrayList<Uri>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // Define the interface to handle click events
    interface OnClickListener {
        fun onItemClick(position: Int)
    }
    interface OnLongClickListener {
        fun onItemLongClick(position: Int)
    }

    private var listener: OnClickListener? = null
    private var longClickListener: OnLongClickListener? = null

    // Set the click listener for each item
    fun setOnClickListener(listener: OnClickListener?) {
        this.listener = listener
    }
    fun setOnLongClickListener(listener: OnLongClickListener?) {
        this.longClickListener = listener
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textView)
//        val del: Button = view.findViewById(R.id.button3)
//        val imageView: ImageView = view.findViewById(R.id.imageView)

        init {
            // Define click listener for the ViewHolder's View
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Notify the interface of the click event
                    listener?.onItemClick(position)
                }
            }
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Notify the interface of the click event
                    longClickListener?.onItemLongClick(position)
                }
                true
            }

//            itemView.setOnLongClickListener { view ->
//                val alertDialogBuilder = AlertDialog.Builder(view.context)
//                alertDialogBuilder.setTitle("Delete Account")
//                alertDialogBuilder.setMessage("Are you sure you want to delete this Account?")
//                alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
//                    c.removeAt(adapterPosition)
//                    notifyItemRemoved(adapterPosition)
//                }
//                alertDialogBuilder.setNegativeButton("No") { dialog, which ->
//
//                }
//                alertDialogBuilder.show()
//                true
//            }

        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details, parent, false)

        return ViewHolder(view)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = c[position].name
//        Glide.with(this@CustomAdapter).load(c[position].img).into(viewHolder.imageView)
//        viewHolder.imageView.setImageURI(c[position].img)


//        viewHolder.itemView.setOnLongClickListener {
//            longClickListener?.onItemLongClick(position)
//            true
//        }

        setAnimation(viewHolder,position)



    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = c.size

    // new code

    fun setAnimation(viewHolder: ViewHolder,position: Int){

            val slideIn = AnimationUtils.loadAnimation(
                viewHolder.itemView.context,
                android.R.anim.slide_in_left
            )
            viewHolder.itemView.startAnimation(slideIn)

    }
}
