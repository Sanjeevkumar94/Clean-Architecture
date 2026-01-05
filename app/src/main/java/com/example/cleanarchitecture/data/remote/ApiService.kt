package com.example.cleanarchitecture.data.remote

import com.example.cleanarchitecture.data.model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    suspend fun getImage( @Query("key") key:String="54014738-670659c46e000a44a73e5bcba",
                          @Query("q") query:String
                          ):PixabayResponse
}