package com.forbes.cat.catalogue

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//private fun createDefaultOkHttpClient(): OkHttpClient {
//    val interceptor = HttpLoggingInterceptor()
//    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//    return OkHttpClient().newBuilder()
//        .addInterceptor(interceptor)
//        .build()
//}

interface BreedService {
    @GET("/v1/images/search")
    suspend fun getImages(
        @Query("api_key") apiKey: String,
        @Query("has_breeds") hasBreeds: Int,
        @Query("limit") limit: Int,
    ):List<ImageResponse>

    @GET("/v1/images/{id}")
    suspend fun getCatInfo(
        @Path("id") id:String
    ):IdResponse

    @GET("/v1/images/search")
    suspend fun getFavouriteCatExample(
        @Query("api_key") apiKey: String,
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int = 1,
    ): List<ImageResponse>
}


object BreedApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
//        .client(createDefaultOkHttpClient())
        .build()
    val breedService:BreedService= retrofit.create(BreedService::class.java)
}