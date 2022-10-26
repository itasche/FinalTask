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
        val query = "CREATE TABLE $TABLE_NAME($ROW_COL INTEGER PRIMARY KEY, $NAME_COL TEXT NOT NULL, $URL_COL TEXT NOT NULL)"
        db!!.execSQL(query)
        Log.d(TAG, "db.execSQL(\"CREATE TABLE $TABLE_NAME($ROW_COL INTEGER PRIMARY KEY, $NAME_COL TEXT, $URL_COL TEXT)\" вызвана")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {  }

    override fun getWritableDatabase(): SQLiteDatabase {
        val db = super.getWritableDatabase()
        Log.d(TAG, "Результат вызова \"val db = this.writableDatabase\" (геттера):\ndb = $db\nСтатус базы данных: db.isOpen = ${db.isOpen}")
        return db
    }

    fun dropDB() {
        Log.d(TAG, "dropDB() вызвана")

        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        Log.d(TAG, "execSQL(\"DROP TABLE IF EXISTS $TABLE_NAME\") вызвана")

        onCreate(db)
        db.close()
        Log.d(TAG, "db.close() вызвана\nСтатус базы данных: db.isOpen = ${db.isOpen}")
    }

    fun addName(name: String, url: String) {
        Log.d(TAG, "addName(...) вызвана")

        var url: String = url
        if (!url.contains("://")) url = "http://$url"

        val values = ContentValues().apply {
            put(NAME_COL, name)
            put(URL_COL, url)
        }

        val db = this.writableDatabase

        Log.d(TAG, "db.insert(...) вызвана, возвращено: ${
            db.insert(TABLE_NAME, null, values)
        }")
        db.close()
        Log.d(TAG, "db.close() вызвана. Статус базы данных: db.isOpen = ${db.isOpen}")
    }

    fun editName(row: Int, name: String, url: String) {
        Log.d(TAG, "editName(...) вызвана")

        var url: String = url
        if (!url.contains("://")) url = "http://$url"

        val values = ContentValues().apply {
            put(ROW_COL, row)
            put(NAME_COL, name)
            put(URL_COL, url)
        }

        val db = this.writableDatabase

        Log.d(TAG, "db.replace(...) вызвана, возвращено: ${
            db.replace(TABLE_NAME, null, values)
        }")
        db.close()
        Log.d(TAG, "db.close() вызвана. Статус базы данных: db.isOpen = ${db.isOpen}")
    }

    fun deleteName(name: String) {
        Log.d(TAG, "deleteName(...) вызвана")

        val db = this.writableDatabase

        Log.d(TAG, "db.delete(...) вызвана, возвращено: ${
            db.delete(TABLE_NAME, NAME_COL + "= ?", arrayOf(name))
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

/* fun nameAlreadyExists(name: String): Boolean {
* val db = this.readableDatabase
*
* Здесь должен быть текст запроса к SQLite для проверки наличия name в NAME_COL,
* но я пока не знаю SQL =(
* так что реализую проверку на наличие не в базе данных, а в модели данных
* для адаптера (т.е. в dataList: ArrayList<DataModel>)
*
* }*/

    companion object {
        private const val DATABASE_NAME = "finaltask"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "bookmarks"
        const val ROW_COL = "row_col"
        const val NAME_COL = "name_col"
        const val URL_COL = "url_col"
    }

}