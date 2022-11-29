package com.example.lifehacktask.data.api

import com.example.lifehacktask.data.model.Company
import retrofit2.Call
import retrofit2.http.GET

interface CompaniesService {
    @GET("/test_task/test.php")
    suspend fun searchForCompanies(): List<Company>

//    @GET("/test.php/")
//    suspend fun searchForCompany(
//        @Query("id") idCompany: Int
//    ): Response<Company>
}