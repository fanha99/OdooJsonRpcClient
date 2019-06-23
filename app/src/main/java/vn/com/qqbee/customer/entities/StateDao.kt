package vn.com.qqbee.customer.entities

import androidx.room.Dao
import androidx.room.Query
import vn.com.qqbee.core.persistence.OdooDao

@Dao
interface StateDao : OdooDao {

    @Query("SELECT COUNT(*) FROM `res.country.state`")
    override fun getCount(): Int

}
