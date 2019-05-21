package vn.com.qqbee.core.web.dataset.searchread

import vn.com.qqbee.core.entities.dataset.searchread.SearchRead
import vn.com.qqbee.core.entities.dataset.searchread.SearchReadReqBody
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchReadRequest {

    companion object {
        const val Route = "/web/dataset/search_read"
    }

    @POST(Route)
    fun searchRead(
            @Body searchReadReqBody: SearchReadReqBody
    ): Observable<Response<SearchRead>>

    @POST(Route)
    fun searchReadW(
        @Body searchReadReqBody: SearchReadReqBody
    ): Call<SearchRead>
}
