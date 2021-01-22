package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Santri
import kotlinx.android.synthetic.main.activity_edit_santri.*
import kotlinx.android.synthetic.main.activity_edit_pesantren.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSantriActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var santriId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_santri)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveSantri.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.santriDao().addSantri(
                    Santri(0, txt_nama.text.toString(), Integer.parseInt(txt_umur.text.toString()), Integer.parseInt(txt_nohp.text.toString()) )
                )
                finish()
            }
        }
        btn_updateSantri.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.santriDao().updateSantri(
                    Santri(santriId, txt_nama.text.toString(), Integer.parseInt(txt_umur.text.toString()), Integer.parseInt(txt_nohp.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateSantri.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveSantri.visibility = View.GONE
                btn_updateSantri.visibility = View.GONE
                getSantri()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveSantri.visibility = View.GONE
                getSantri()
            }
        }
    }

    fun getSantri() {
        santriId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val santris =  db.santriDao().getSantri( santriId )[0]
            txt_nama.setText( santris.nama )
            txt_umur.setText( santris.umur.toString() )
            txt_nohp.setText( santris.nohp.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}