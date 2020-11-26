package com.badcompany.remote

import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.ResponseWrapper
import com.badcompany.remote.model.RespFormatter
import retrofit2.HttpException

object ResponseFormatter {

    suspend fun <T> getFormattedResponse(action: suspend () -> RespFormatter<T>): ResponseWrapper<T> {
        return try {
            val resp = action()
            when {
                resp.code == 1 && resp.data != null -> ResponseSuccess(resp.data)
                else -> ResponseError(resp.message, resp.code)
            }
        } catch (e: HttpException) {
            ResponseError("HTTP EXCEPTION")
//            ResponseError(JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
//                          e.code())
        } catch (e: Exception) {
            ResponseError(message = e.localizedMessage)
        }
    }

}