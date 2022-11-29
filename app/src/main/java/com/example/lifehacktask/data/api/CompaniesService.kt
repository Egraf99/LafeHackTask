package com.example.lifehacktask.data.api

import com.example.lifehacktask.data.model.Company
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CompaniesService {
    @GET("/test_task/test.php")
    suspend fun searchForCompanies(): List<Company>

    @GET("/test_task/test.php")
    suspend fun searchForCompany(
        @Query("id") id: Int
    ): Company

//    @GET("/test.php/")
//    suspend fun searchForCompany(
//        @Query("id") idCompany: Int
//    ): Response<Company>
}