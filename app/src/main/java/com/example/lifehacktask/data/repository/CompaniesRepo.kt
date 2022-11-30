package com.example.lifehacktask.data.repository

import com.example.lifehacktask.data.api.ApiHelper

class CompaniesRepo(private val apiHelper: ApiHelper) {
    suspend fun getCompanies() = apiHelper.getCompanies()
    suspend fun getCompany(id: Int) = apiHelper.getCompany(id)
}

