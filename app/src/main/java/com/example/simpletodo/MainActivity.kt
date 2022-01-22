package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // set layout to activity_main

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item
                listOfTasks.removeAt(position)
                // 2. notify the adapter that the data has chagned
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        loadItems()

        // 1. look up recyclerView in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        // 2. Initialize contacts
        // 3. Create adapter that passes the contacts
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // 4. Attach the adapter to the recyclerView
        recyclerView.adapter = adapter
        // 5. Set a layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up button and input field for user to input tasks
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button
        // and then set an onclick Listener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text that the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            // 2. Add the string to our list of tasks: ListOfTasks
            listOfTasks.add(userInputtedTask)
            // need to notify the adapeter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1) // insert at the very end of the list
            // 3. Reset text field
            inputTextField.setText("")
            saveItems()
        }
    }

        // Save the user input data by writing and reading from a file
        // 1. create a method to get the data file
        fun getDataFile(): File {
            // every line is going to represent a task 
            return File(filesDir, "data.txt")
        }
        // 2. a method to load the file by reading every line 
        fun loadItems(){
            try{ listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset()) }
            catch(ioException: IOException){ ioException.printStackTrace() }
         }

        // 3. save items by writing to file
        fun saveItems(){
            try{ org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks) }
            catch(ioException: IOException){ ioException.printStackTrace()}
        }

}