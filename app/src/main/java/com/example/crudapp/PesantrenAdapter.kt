package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Pesantren
import kotlinx.android.synthetic.main.adapter_pesantren.view.*

class PesantrenAdapter (private val AllPesantren: ArrayList<Pesantren>, private val listener: OnAdapterListener) : RecyclerView.Adapter<PesantrenAdapter.PesantrenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesantrenViewHolder {
        return PesantrenViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_pesantren, parent, false)
        )
    }

    override fun getItemCount() = AllPesantren.size

    override fun onBindViewHolder(holder: PesantrenViewHolder, position: Int) {
        val pesantren = AllPesantren[position]
        holder.view.text_alamat.text = pesantren.alamat
        holder.view.text_alamat.setOnClickListener {
            listener.onClick(pesantren)
        }
        holder.view.icon_deletePesantren.setOnClickListener {
            listener.onDelete(pesantren)
        }
        holder.view.icon_editPesantren.setOnClickListener {
            listener.onUpdate(pesantren)
        }
    }

    class PesantrenViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Pesantren>) {
        AllPesantren.clear()
        AllPesantren.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(pesantren: Pesantren)
        fun onDelete(pesantren: Pesantren)
        fun onUpdate(pesantren: Pesantren)
    }

}