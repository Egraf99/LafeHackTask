package com.example.lifehacktask.ui

import androidx.lifecycle.ViewModel
import com.example.lifehacktask.data.CompaniesRepo
import com.example.lifehacktask.data.Company

class CompaniesListViewModel: ViewModel() {
    private val repo = CompaniesRepo.get()
    fun getCompanies(): List<Company> = repo.getCompanies()
}