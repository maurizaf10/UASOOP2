package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Santri
import kotlinx.android.synthetic.main.activity_santri.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SantriActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var santriAdapter: SantriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_santri)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadSantri()
    }

    fun loadSantri(){
        CoroutineScope(Dispatchers.IO).launch {
            val allSantri = db.santriDao().getAllSantri()
            Log.d("SantriActivity", "dbResponse: $allSantri")
            withContext(Dispatchers.Main) {
                santriAdapter.setData(allSantri)
            }
        }
    }

    fun setupListener() {
        btn_createSantri.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        santriAdapter = SantriAdapter(arrayListOf(), object: SantriAdapter.OnAdapterListener {
            override fun onClick(santri: Santri) {
                // read detail
                intentEdit(santri.id, Constant.TYPE_READ)
            }

            override fun onDelete(santri: Santri) {
                // delete data
                deleteDialog(santri)
            }

            override fun onUpdate(santri: Santri) {
                // edit data
                intentEdit(santri.id, Constant.TYPE_UPDATE)
            }

        })
        list_santri.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = santriAdapter
        }
    }

    fun intentEdit(santriId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditSantriActivity::class.java)
                .putExtra("intent_id", santriId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(santri: Santri) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.santriDao().deleteSantri(santri)
                    loadSantri()
                }
            }
        }
        alertDialog.show()
    }
}