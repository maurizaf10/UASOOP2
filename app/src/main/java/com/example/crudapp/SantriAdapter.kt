package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Santri
import kotlinx.android.synthetic.main.adapter_santri.view.*

class SantriAdapter (private val allSantri: ArrayList<Santri>, private val listener: OnAdapterListener) : RecyclerView.Adapter<SantriAdapter.SantriViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SantriViewHolder {
        return SantriViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_santri, parent, false)
        )
    }

    override fun getItemCount() = allSantri.size

    override fun onBindViewHolder(holder: SantriViewHolder, position: Int) {
        val santri = allSantri[position]
        holder.view.text_nama.text = santri.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(santri)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(santri)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(santri)
        }
    }

    class SantriViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Santri>) {
        allSantri.clear()
        allSantri.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(santri: Santri)
        fun onDelete(santri: Santri)
        fun onUpdate(santri: Santri)
    }
}