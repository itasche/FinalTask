package com.example.finaltask

import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private var dataList: ArrayList<DataModel>,
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvItemRow = itemView.findViewById<TextView>(R.id.tvItemRow)
        val tvItemName = itemView.findViewById<TextView>(R.id.tvItemName)
        val tvItemUrl = itemView.findViewById<TextView>(R.id.tvItemUrl)

        val ibItemDelete = itemView.findViewById<ImageButton>(R.id.ibItemDelete)
        val ibItemEdit = itemView.findViewById<ImageButton>(R.id.ibItemEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_bookmark,
            parent,
            false,
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.tvItemRow.text = dataList[position].itemRowNumber as String
        holder.tvItemName.text = dataList[position].itemName
        holder.tvItemUrl.text = dataList[position].itemUrl

        holder.ibItemDelete.setOnClickListener {
            // фрагмент вью
        }
        holder.ibItemEdit.setOnClickListener {
            // фрагмент вью

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}