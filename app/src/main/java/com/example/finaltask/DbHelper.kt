package com.example.finaltask

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "DbHelper.kt"

class DbHelper (
    context: Context,
    factory: SQLiteDatabase.CursorFactory?,
): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME($ROW_COL INTEGER PRIMARY KEY, $NAME_COL TEXT, $URL_COL TEXT)"
        db!!.execSQL(query)
        Log.d(TAG, "db.execSQL(\"CREATE TABLE $TABLE_NAME($ROW_COL INTEGER PRIMARY KEY, $NAME_COL TEXT, $URL_COL TEXT)\" вызвана")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun onUpgrade(db: SQLiteDatabase?) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        Log.d(TAG, "execSQL(\"DROP TABLE IF EXISTS $TABLE_NAME\") вызвана")
        onCreate(db)
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        val db = super.getWritableDatabase()
        Log.d(TAG, "Результат вызова \"val db = this.writableDatabase\" (геттера):\ndb = $db\nСтатус базы данных: db.isOpen = ${db.isOpen}")
        return db
    }

    fun dropDB() {
        Log.d(TAG, "dropDB() вызвана")

        val db = this.writableDatabase
        onUpgrade(db)
        db.close()
        Log.d(TAG, "db.close() вызвана\nСтатус базы данных: db.isOpen = ${db.isOpen}")
    }

    fun addName(name: String, url: String) {
        Log.d(TAG, "addName(...) вызвана")

        val values = ContentValues()
        val db = this.writableDatabase

        values.put(NAME_COL, name)
        values.put(URL_COL, url)
        Log.d(TAG, "db.insert(...) вызвана, возвращено: ${
            db.insert(TABLE_NAME, null, values)
        }")
        db.close()
        Log.d(TAG, "db.close() вызвана. Статус базы данных: db.isOpen = ${db.isOpen}")
    }

    fun getName(): Cursor? {
        Log.d(TAG, "getName() вызвана")
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        Log.d(TAG, "db.rawQuery(\"SELECT * FROM $TABLE_NAME\", null) вызвана:\ncursor = $cursor")
        return cursor
    }

    companion object {
        private const val DATABASE_NAME = "finaltask"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "bookmarks"
        const val ROW_COL = "row_col"
        const val NAME_COL = "name_col"
        const val URL_COL = "url_col"
    }

}