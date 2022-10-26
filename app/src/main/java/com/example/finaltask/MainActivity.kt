package com.example.finaltask

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity.kt"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataModel.refreshDataList(this)
        val dataAdapter = DataAdapter(DataModel.dataList)

        findViewById<RecyclerView>(R.id.rvWebBookmarks).adapter = dataAdapter

        findViewById<Button>(R.id.btNewBookmark).setOnClickListener {
            Log.d(TAG,"btNewBookmark click detected")

            val addFragmentTransaction = supportFragmentManager.beginTransaction()
            val addFragment = AddEditFragment(dataAdapter)
            val addFragmentBundle = Bundle()

            addFragmentBundle.putString(AddEditFragment.ACTION_KEY, AddEditFragment.ACTION_ADD)
            addFragment.arguments = addFragmentBundle
            addFragmentTransaction.addToBackStack("")
            addFragmentTransaction.add(R.id.flWebBookmarks, addFragment).commit()
            Log.d(TAG, "Commit done")
        }
    }
}