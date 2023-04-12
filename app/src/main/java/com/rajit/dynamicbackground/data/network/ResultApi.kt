package com.rajit.dynamicbackground.data.network

import com.rajit.dynamicbackground.data.model.Result
import retrofit2.Response
import retrofit2.http.GET

// Interface that is used to fetch items from network
interface ResultApi {

    @GET("list.json")
    suspend fun getList(): Response<Result>

}