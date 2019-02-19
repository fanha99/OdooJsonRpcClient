package vn.com.qqbee.core.web.database.listdb

import vn.com.qqbee.core.entities.database.listdb.ListDb
import vn.com.qqbee.core.entities.database.listdb.ListDbReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ListDbRequest {

    @POST("/web/database/list")
    fun listDb(
            @Body listDbReqBody: ListDbReqBody
    ): Observable<Response<ListDb>>
}