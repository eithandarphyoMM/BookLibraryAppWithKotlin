package com.example.booklibraryappwithkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var add_button: FloatingActionButton
    var empty_imageview: ImageView? = null
    var no_data: TextView? = null
    var myDB: MyDatabaseHelper? = null
    var book_id: ArrayList<String>? = null
    var book_title: ArrayList<String>? = null
    var book_author: ArrayList<String>? = null
    var book_pages: ArrayList<String>? = null
    var customAdapter: CustomAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        add_button = findViewById(R.id.add_button)
        empty_imageview = findViewById(R.id.empty_imageview)
        no_data = findViewById(R.id.no_data)
        add_button.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@MainActivity,
                AddActivity::class.java
            )
            startActivity(intent)
        })
        myDB = MyDatabaseHelper(this@MainActivity)
        book_id = ArrayList()
        book_title = ArrayList()
        book_author = ArrayList()
        book_pages = ArrayList()
        storeDataInArrays()
        customAdapter = CustomAdapter(
            this@MainActivity, this,
            book_id!!, book_title!!, book_author!!, book_pages!!
        )
        recyclerView.setAdapter(customAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this@MainActivity))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    fun storeDataInArrays() {
        val cursor = myDB!!.readAllData()
        if (cursor!!.count == 0) {
            empty_imageview!!.visibility = View.VISIBLE
            no_data!!.visibility = View.VISIBLE
        } else {
            while (cursor.moveToNext()) {
                book_id!!.add(cursor.getString(0))
                book_title!!.add(cursor.getString(1))
                book_author!!.add(cursor.getString(2))
                book_pages!!.add(cursor.getString(3))
            }
            empty_imageview!!.visibility = View.GONE
            no_data!!.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            confirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete ALL ?")
        builder.setMessage("Are you sure you want to delete all data?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            val myDB = MyDatabaseHelper(this@MainActivity)
            myDB.deleteAllData()
            //Refresh Activity
            val intent = Intent(
                this@MainActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, which -> }
        builder.create().show()
    }
}