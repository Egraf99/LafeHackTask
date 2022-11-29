package com.example.lifehacktask

import android.app.Application
import com.example.lifehacktask.data.CompaniesRepo

class CompaniesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CompaniesRepo.initialize()
    }
}