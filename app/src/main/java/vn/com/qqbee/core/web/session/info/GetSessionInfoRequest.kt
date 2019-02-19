package vn.com.qqbee.core.web.session.info

import vn.com.qqbee.core.entities.session.info.GetSessionInfo
import vn.com.qqbee.core.entities.session.info.GetSessionInfoReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetSessionInfoRequest {

    @POST("/web/session/get_session_info")
    fun getSessionInfo(
            @Body getSessionInfoReqBody: GetSessionInfoReqBody
    ): Observable<Response<GetSessionInfo>>
}
