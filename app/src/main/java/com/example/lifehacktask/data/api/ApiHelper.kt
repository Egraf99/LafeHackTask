package com.example.lifehacktask.data.api

class ApiHelper(private val companiesService: CompaniesService) {
    suspend fun getCompanies() = companiesService.searchForCompanies()
}