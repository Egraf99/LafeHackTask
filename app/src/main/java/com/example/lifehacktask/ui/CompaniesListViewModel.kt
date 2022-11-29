package com.example.lifehacktask.ui

import androidx.lifecycle.ViewModel
import com.example.lifehacktask.data.Company

class CompaniesListViewModel: ViewModel() {
    fun getCompanies(): List<Company> = listOf(
        Company(id = 1, name = "Test 1"),
        Company(id = 2, name = "Test 2"),
        Company(id = 3, name = "Test 3"),
        Company(id = 4, name = "Test 4"),
    )
}