package com.rajit.dynamicbackground.data.repository

import com.rajit.dynamicbackground.data.network.ResultApi
import javax.inject.Inject

// Repository
class ArtistRepository @Inject constructor(
    private val api: ResultApi
) {

    suspend fun getList() = api.getList()

}