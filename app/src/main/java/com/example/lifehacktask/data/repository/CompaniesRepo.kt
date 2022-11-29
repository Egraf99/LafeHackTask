package com.example.lifehacktask.data.repository

import com.example.lifehacktask.data.api.ApiHelper
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.data.api.CompaniesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompaniesRepo(private val apiHelper: ApiHelper) {
//
//    fun getCompanies(): List<Company> = listOf(
//        Company(id = 1, name = "Test 1", ""),
//        Company(id = 2, name = "Test 2", ""),
//        Company(id = 3, name = "Test 3", ""),
//        Company(id = 4, name = "Test 4", ""),
//    )
    suspend fun getCompanies() = apiHelper.getCompanies()
}

