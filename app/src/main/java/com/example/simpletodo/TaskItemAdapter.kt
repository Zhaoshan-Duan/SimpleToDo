package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *  The bridge tells the recyclerview how to display the data we give it
 */
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener{
        // define what happens when a specific item is clicked
        fun onItemLongClicked(position: Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    //  tell recyclerView how to layout each specific item inside the recyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    //  For this case: use listOfItems to populate whatever is inside the layout of View Holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 1. Get the data model based on position
        val item  = listOfItems.get(position)
        // 2. Set item views to be the text specific task is
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    //  look at the layout we will use, grab the references for onBindViewHolder to use
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store reference to elements in our layout view
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }


}