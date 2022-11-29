package com.example.lifehacktask.data.repository

import com.example.lifehacktask.data.api.ApiHelper
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.data.api.CompaniesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompaniesRepo(private val apiHelper: ApiHelper) {
    suspend fun getCompanies() = apiHelper.getCompanies()
    suspend fun getCompany(id: Int) = apiHelper.getCompany(id)
}

