package com.example.lifehacktask.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
    private const val BASE_URL = "https://lifehack.studio/"

    private val getRetrofit: () -> Retrofit = {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val companiesService: CompaniesService = getRetrofit().create(CompaniesService::class.java)
}}