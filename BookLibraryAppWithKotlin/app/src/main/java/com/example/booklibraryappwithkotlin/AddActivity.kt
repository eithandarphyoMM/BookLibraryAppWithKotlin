package com.example.booklibraryappwithkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class AddActivity : AppCompatActivity() {
    private lateinit var title_input: EditText
    private lateinit var author_input: EditText
    private lateinit var pages_input: EditText
    private lateinit var add_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        title_input = findViewById(R.id.title_input)
        author_input = findViewById(R.id.author_input)
        pages_input = findViewById(R.id.pages_input)
        add_button = findViewById(R.id.add_button)

        add_button.setOnClickListener {
            val myDB = MyDatabaseHelper(this@AddActivity)
            myDB.addBook(
                title_input.text.toString().trim(),
                author_input.text.toString().trim(),
                pages_input.text.toString().trim().toIntOrNull() ?: 0
            )
        }
    }
}