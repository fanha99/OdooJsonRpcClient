package vn.com.qqbee.core

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.com.qqbee.App
import vn.com.qqbee.core.persistence.AppTypeConverters
import vn.com.qqbee.customer.entities.*

@Database(
    entities = [
        /* Add Room Entities here: BEGIN */

        Customer::class, // res.partner
        Country::class, // res.partner
        State::class // res.partner

        /* Add Room Entities here: END */
    ], version = 1, exportSchema = true
)
@TypeConverters(AppTypeConverters::class)
abstract class OdooDatabase : RoomDatabase() {

    companion object {

        lateinit var app: App

        var database: OdooDatabase? = null
            get() {
                if (field == null) {
                    field = Room.databaseBuilder(app, OdooDatabase::class.java, "${Odoo.user.androidName}.db").build()
                }
                return field
            }
    }

    /* Add Room DAO(s) here: BEGIN */

    abstract fun customerDao(): CustomerDao
    abstract fun countryDao(): CountryDao
    abstract fun stateDao(): StateDao

    /* Add Room DAO(s) here: END */
}
