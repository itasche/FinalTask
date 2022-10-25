package com.example.finaltask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

private const val TAG = "MainActivity.kt"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataList = mutableListOf<DataModel>()
        val db = DbHelper(this, null)
        val cursor = db.getName()

        if (cursor!!.moveToFirst()) {
            dataList.clear()
            dataList.add(DataModel(
                cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ROW_COL)),
                cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAME_COL)),
                cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.URL_COL)),
            ))

            while (cursor.moveToNext()) {
                dataList.add(DataModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ROW_COL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAME_COL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.URL_COL)),
                ))
            }
        }

        cursor.close()
        db.close()
        Log.d(TAG, "cursor.close() вызвана. Статус курсора: cursor.isClosed = ${cursor.isClosed}\ndb.close() вызвана, db = $db\n")

        findViewById<Button>(R.id.btNewBookmark).setOnClickListener {
            Log.d(TAG,"Click detected")

            val addFragmentTransaction = supportFragmentManager.beginTransaction()
            val addFragment = AddEditFragment()
            val addFragmentBundle = Bundle()

            addFragmentBundle.putString(AddEditFragment.ACTION_KEY, AddEditFragment.ACTION_ADD)
            addFragment.arguments = addFragmentBundle
            addFragmentTransaction.addToBackStack("")
            addFragmentTransaction.add(R.id.flWebBookmarks, addFragment).commitNow()
            Log.d(TAG, "Commit done")

//            addFragmentTransaction.remove(addFragment).commitNow()
//            (it.parent as ViewGroup).removeView(it)
//            layoutInflater.inflate(R.layout.fragment_add_edit, findViewById(R.id.flWebBookmarks), true).invalidate()
//            Log.d(TAG, "Инвалидация завершена")
        }

    }
}