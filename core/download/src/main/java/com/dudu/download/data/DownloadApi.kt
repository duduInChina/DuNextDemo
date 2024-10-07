package com.dudu.download.data

import com.dudu.network.request.IRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/24
 *     desc   :
 * </pre>
 */
interface DownloadApi {

    @Streaming
    @GET
    suspend fun coroutinesDownloadGet(
        @Url url: String,
        @HeaderMap headers: Map<String, String>
    ): ResponseBody

    @Streaming
    @POST
    suspend fun coroutinesDownloadPost(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
        @Body data: IRequest
    ): ResponseBody

    @Streaming
    @POST
    suspend fun coroutinesDownloadPost(
        @Url url: String,
        @HeaderMap headers: Map<String, String>
    ): ResponseBody

}