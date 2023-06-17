package web.connect.demo.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "formdb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "myform"
        private const val ID_COL = "id"
        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
        private const val MOBILE = "mobile"
        private const val BLOOD_GROUP = "bloodGroup"
        private const val ADDRESS = "address"
        private const val CITY = "city"
        private const val STATE = "state"
        private const val COUNTRY = "country"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIRST_NAME TEXT, " +
                "$LAST_NAME TEXT, " +
                "$MOBILE TEXT, " +
                "$BLOOD_GROUP TEXT, " +
                "$ADDRESS TEXT, " +
                "$CITY TEXT, " +
                "$STATE TEXT, " +
                "$COUNTRY TEXT)"
        db.execSQL(query)
    }

    fun addNewForm(firstName: String, lastName: String, mobile: String, bloodGroup: String,
                   address: String, city: String, state: String, country: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(FIRST_NAME, firstName)
            put(LAST_NAME, lastName)
            put(MOBILE, mobile)
            put(BLOOD_GROUP, bloodGroup)
            put(ADDRESS, address)
            put(CITY, city)
            put(STATE, state)
            put(COUNTRY, country)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllForms(): List<String> {
        val forms = mutableListOf<String>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        cursor?.use {
            if (cursor.moveToFirst()) {
                do {
                    val firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    val lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))
                    val mobile = cursor.getString(cursor.getColumnIndex(MOBILE))
                    val bloodGroup = cursor.getString(cursor.getColumnIndex(BLOOD_GROUP))
                    val address = cursor.getString(cursor.getColumnIndex(ADDRESS))
                    val city = cursor.getString(cursor.getColumnIndex(CITY))
                    val state = cursor.getString(cursor.getColumnIndex(STATE))
                    val country = cursor.getString(cursor.getColumnIndex(COUNTRY))

                    val formData = "First Name: $firstName, Last Name: $lastName" +
                            "Mobile: $mobile, Blood Group: $bloodGroup, Address: $address" +
                            "City: $city, State: $state, Country: $country"
                    forms.add(formData)
                } while (cursor.moveToNext())
            }
        }

        db.close()
        return forms
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
