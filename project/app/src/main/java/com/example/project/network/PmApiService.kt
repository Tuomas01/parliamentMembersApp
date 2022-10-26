package com.example.project.network

import com.example.project.database.parliamentmembers.ParliamentMembers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/*
Class for getting the Json file, where the data of Pms is stored
Using retrofit and moshi libraries, builds a converter factory, which converts Json file into ParliamentMembers objects
*/
class PmApiService {
    companion object {
        private const val BASE_URL = "https://users.metropolia.fi/~tuomheik/test/"
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).build()
    }

    /*
    Make the GET request to get the data from the website where it's stored.
    getPmInfo() contains the data from the website as ParliamentMembers objects in a list
    */
    interface PmApiService {
        @GET("info")
        suspend fun getPmInfo(): List<ParliamentMembers>
    }

    object PmApi {
        val retrofitService: PmApiService by lazy {
            retrofit.create(PmApiService::class.java)
        }
    }
}