package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Pesantren
import kotlinx.android.synthetic.main.activity_edit_santri.*
import kotlinx.android.synthetic.main.activity_edit_pesantren.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPesantrenActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var pesantrenId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pesantren)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_savePesantren.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.pesantrenDao().addPesantren(
                    Pesantren(0, txt_namapesantren.text.toString(), txt_alamat.text.toString())
                )
                finish()
            }
        }
        btn_updatePesantren.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.pesantrenDao().updatePesantren(
                    Pesantren(pesantrenId, txt_namapesantren.text.toString(), txt_alamat.text.toString())
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
                btn_updatePesantren.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_savePesantren.visibility = View.GONE
                btn_updatePesantren.visibility = View.GONE
                getPesantren()
            }
            Constant.TYPE_UPDATE -> {
                btn_savePesantren.visibility = View.GONE
                getPesantren()
            }
        }
    }

    fun getPesantren() {
        pesantrenId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val pesantrens =  db.pesantrenDao().getPesantren( pesantrenId )[0]
            txt_namapesantren.setText( pesantrens.namapesantren )
            txt_alamat.setText( pesantrens.alamat )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}