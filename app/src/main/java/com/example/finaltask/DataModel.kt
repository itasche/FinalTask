package com.example.finaltask

import android.content.Context
import android.util.Log

private const val TAG = "DataModel.kt"

class DataModel (
    var itemRowNumber: Int,
    var itemName: String,
    var itemUrl: String
) {
    companion object {
        val dataList = ArrayList<DataModel>()

        fun refreshDataList(context: Context) {
            val db = DbHelper(context, null)
            val cursor = db.getName()

            if (cursor!!.moveToFirst()) {
                dataList.clear()
                dataList.add(
                    DataModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ROW_COL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAME_COL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.URL_COL)),
                    )
                )

                while (cursor.moveToNext()) {
                    dataList.add(
                        DataModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ROW_COL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAME_COL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.URL_COL)),
                        )
                    )
                }
            }

            cursor.close()
            db.close()
            Log.d(
                TAG,
                "cursor.close() вызвана. Статус курсора: cursor.isClosed = ${cursor.isClosed}\ndb.close() вызвана, db = $db\n"
            )
        }

        fun nameAlreadyExists(name: String): Boolean {
            dataList.forEach {
                if (it.itemName == name) return true
            }
            return false
        }
    }
}