package vn.com.qqbee.core.web.session.authenticate

import vn.com.qqbee.core.entities.session.authenticate.Authenticate
import vn.com.qqbee.core.entities.session.authenticate.AuthenticateReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticateRequest {

    @POST("/web/session/authenticate")
    fun authenticate(
            @Body authenticateReqBody: AuthenticateReqBody
    ): Observable<Response<Authenticate>>
}
