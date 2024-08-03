package tech.johnnydev.learnsqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import tech.johnnydev.learnsqlite.entity.Cadastro

class DatabaseHandler (context : Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
     db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert( cadastro: Cadastro){
        val db = writableDatabase

        val values = ContentValues()
        values.put("name", cadastro.name)
        values.put("phone", cadastro.phone)
        db.insert(TABLE_NAME, null, values)
    }

    fun update(cadastro: Cadastro){
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", cadastro.name)
        values.put("phone", cadastro.phone)
        db.update(TABLE_NAME, values, "_id = ?", arrayOf(cadastro._id.toString()))
    }

    fun delete(_id: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, "_id=$_id", null)
    }


    fun find(id: Int) :Cadastro?{
        val db = readableDatabase
        val register = db.query(TABLE_NAME,
            null,
            "_id=$id",
            null,
            null,
            null,
            null,
            )

        if (register.moveToNext()){
            val cadastro = Cadastro(
                register.getInt(CODE),
                register.getString(NAME),
                register.getString(PHONE)
            )

            return cadastro

        }else{
            return null
        }
    }

    fun list() : MutableList<Cadastro>{
        val db = readableDatabase
        val registers = db.query(TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
        )

        val list = mutableListOf<Cadastro>()

        while (registers.moveToNext()){
            val cadastro = Cadastro(
                registers.getInt(CODE),
                registers.getString(NAME),
                registers.getString(PHONE)
            )

            list.add(cadastro)
        }

        return list
    }






    companion object {
        private  const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private  const val TABLE_NAME = "cadastro"
        private const val CODE = 0
        private const val NAME = 1
        private const val PHONE = 2
    }

}


