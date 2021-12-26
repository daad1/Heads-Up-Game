package com.example.headsup

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("celebrities")
    fun getdata(): Call<ArrayList<UsersItem>>
    @GET("celebrities/{pk}")
    fun getspicifdata(@Path("pk")pk:Int, @Query("name")name:String): Call<ArrayList<UserItemPk>>
    @GET("celebrities/")
    fun getdataWithPk2(): Call<ArrayList<UserItemPk>>
    @POST("celebrities/")
    fun postdata(@Body newCele: UsersItem): Call<ArrayList<UsersItem>>
    @PUT("celebrities/{pk}")
    fun updatedata(@Path("pk")pk: Int, @Body updateOne:UsersItem): Call<ArrayList<UsersItem>>
    @DELETE("celebrities/{pk}")
    fun deletedata(@Path("pk")pk: Int ): Call<Void>
}