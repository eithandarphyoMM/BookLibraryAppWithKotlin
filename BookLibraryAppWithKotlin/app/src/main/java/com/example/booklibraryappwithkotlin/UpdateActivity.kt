package com.example.booklibraryappwithkotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class UpdateActivity : AppCompatActivity() {
    var title_input: EditText? = null
    var author_input: EditText? = null
    var pages_input: EditText? = null
    private lateinit var update_button:Button
    private lateinit var delete_button:Button
    var id: String? = null
    var title: String? = null
    var author: String? = null
    var pages: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        title_input = findViewById(R.id.title_input2)
        author_input = findViewById(R.id.author_input2)
        pages_input = findViewById(R.id.pages_input2)
        update_button = findViewById(R.id.update_button)
        delete_button = findViewById(R.id.delete_button)

        //First we call this
        andSetIntentData

        //Set action bar title after getAndSetIntentData method
        val ab = supportActionBar
        ab?.setTitle(title)
        update_button.setOnClickListener(View.OnClickListener { //And only then we call this
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.updateData(id!!, title, author, pages)
        })
        delete_button.setOnClickListener(View.OnClickListener { confirmDialog() })
    }

    val andSetIntentData: Unit
        get() {
            if (intent.hasExtra("id") && intent.hasExtra("title") &&
                intent.hasExtra("author") && intent.hasExtra("pages")
            ) {
                //Getting Data from intent
                id = intent.getStringExtra("id")
                title = intent.getStringExtra("title")
                author = intent.getStringExtra("author")
                pages = intent.getStringExtra("pages")

                //Setting Indent Data
                title_input!!.setText(title)
                author_input!!.setText(author)
                pages_input!!.setText(pages)
            } else {
                Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
            }
        }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRow(id!!)
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, which -> }
        builder.create().show()
    }
}