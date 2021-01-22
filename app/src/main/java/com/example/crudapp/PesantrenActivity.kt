package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Pesantren
import kotlinx.android.synthetic.main.activity_pesantren.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PesantrenActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var pesantrenAdapter: PesantrenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesantren)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadPesantren()
    }

    fun loadPesantren() {
        CoroutineScope(Dispatchers.IO).launch {
            val allPesantren = db.pesantrenDao().getAllPesantren()
            Log.d("PesantrenActivity", "dbResponse: $allPesantren")
            withContext(Dispatchers.Main) {
                pesantrenAdapter.setData(allPesantren)
            }
        }
    }

    fun setupListener() {
        btn_createPesantren.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        pesantrenAdapter = PesantrenAdapter(arrayListOf(), object: PesantrenAdapter.OnAdapterListener {
            override fun onClick(pesantren: Pesantren) {
                intentEdit(pesantren.id, Constant.TYPE_READ)
            }
            override fun onDelete(pesantren: Pesantren) {
                deleteDialog(pesantren)
            }
            override fun onUpdate(pesantren: Pesantren) {
                // edit data
                intentEdit(pesantren.id, Constant.TYPE_UPDATE)
            }

        })
        list_pesantren.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = pesantrenAdapter
        }
    }

    fun intentEdit(pesantrenId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditPesantrenActivity::class.java)
                .putExtra("intent_id", pesantrenId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(pesantren: Pesantren) {
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
                    db.pesantrenDao().deletePesantren(pesantren)
                    loadPesantren()
                }
            }
        }
        alertDialog.show()
    }
}