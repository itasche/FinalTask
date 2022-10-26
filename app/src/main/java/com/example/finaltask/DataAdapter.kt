package com.example.finaltask

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "DataAdapter.kt"

class DataAdapter(
    private var dataList: ArrayList<DataModel>,
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvItemRow: TextView = itemView.findViewById(R.id.tvItemRow)
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvItemUrl: TextView = itemView.findViewById(R.id.tvItemUrl)

        val ibItemDelete: ImageButton = itemView.findViewById(R.id.ibItemDelete)
        val ibItemEdit: ImageButton = itemView.findViewById(R.id.ibItemEdit)
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
        holder.tvItemRow.text = dataList[position].itemRowNumber.toString()
        holder.tvItemName.text = dataList[position].itemName
        holder.tvItemUrl.text = dataList[position].itemUrl

        holder.ibItemDelete.setOnClickListener { outerIt ->

            val confirmView = LayoutInflater.from(outerIt.context)
                .inflate(
                    R.layout.item_request_delete_confirm,
                    outerIt.rootView as ViewGroup,
                    true,
                )

            confirmView.findViewById<Button>(R.id.btDeleteCancel).setOnClickListener {
                (confirmView.rootView as ViewGroup).removeView(confirmView)
            }

            confirmView.findViewById<Button>(R.id.btDeleteConfirm).setOnClickListener {
                DbHelper(it.context,null).deleteName(holder.tvItemName.text.toString())

                Toast.makeText(
                    it.context,
                    R.string.toastDeleted,
                    Toast.LENGTH_LONG,
                ).show()

                refreshDataList(it.context)
                (confirmView.parent as ViewGroup).removeView(confirmView)
            }
            confirmView.invalidate()
            Log.d(TAG, "Инвалидация завершена")
        }

        holder.ibItemEdit.setOnClickListener {
            Log.d(TAG,"btItemEdit click detected")

            val editFragment = AddEditFragment(this)
            val editFragmentTransaction = editFragment.parentFragmentManager.beginTransaction()
            val editFragmentBundle = Bundle()

            editFragment.arguments = editFragmentBundle.apply {
                putString(AddEditFragment.ACTION_KEY, AddEditFragment.ACTION_EDIT)
                putString(DbHelper.ROW_COL, holder.tvItemRow.text.toString())
                putString(DbHelper.NAME_COL, holder.tvItemName.text.toString())
                putString(DbHelper.URL_COL, holder.tvItemUrl.text.toString())
            }

            editFragmentTransaction.addToBackStack("")
            editFragmentTransaction.add(R.id.flWebBookmarks, editFragment).commit()
            Log.d(TAG, "Commit done")

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun refreshDataList(context: Context) {
        DataModel.refreshDataList(context)
        dataList = DataModel.dataList
        notifyDataSetChanged()
    }
}