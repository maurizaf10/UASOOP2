package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_santri.setOnClickListener{
            val intent = Intent(this, SantriActivity::class.java)
            startActivity(intent)
        }

        btn_pesantren.setOnClickListener{
            val intent = Intent(this, PesantrenActivity::class.java)
            startActivity(intent)
        }
    }
}