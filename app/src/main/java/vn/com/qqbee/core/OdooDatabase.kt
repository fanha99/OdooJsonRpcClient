package vn.com.qqbee.core

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.com.qqbee.App
import vn.com.qqbee.core.persistence.AppTypeConverters
import vn.com.qqbee.customer.entities.Customer
import vn.com.qqbee.customer.entities.CustomerDao

@Database(
    entities = [
        /* Add Room Entities here: BEGIN */

        Customer::class // res.partner

        /* Add Room Entities here: END */
    ], version = 1, exportSchema = true
)
@TypeConverters(AppTypeConverters::class)
abstract class OdooDatabase : RoomDatabase() {

    companion object {

        lateinit var app: App

        val dbName: String
            get() = "${Odoo.user.androidName}.db"

        var database: OdooDatabase? = null
            get() {
                if (field == null) {
                    field = Room.databaseBuilder(app, OdooDatabase::class.java, dbName).build()
                }
                return field
            }
    }

    /* Add Room DAO(s) here: BEGIN */

    abstract fun customerDao(): CustomerDao

    /* Add Room DAO(s) here: END */
}
