package vn.com.qqbee.customer.entities

import androidx.room.Dao
import androidx.room.Query
import vn.com.qqbee.core.persistence.OdooDao

@Dao
interface CountryDao : OdooDao {

    @Query("SELECT COUNT(*) FROM `res.country`")
    override fun getCount(): Int

}
