package com.example.lifehacktask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifehacktask.data.api.ApiHelper
import com.example.lifehacktask.data.repository.CompaniesRepo
import com.example.lifehacktask.ui.listCompanies.CompaniesListViewModel
import com.example.lifehacktask.ui.singleCompany.SingleCompanyViewModel

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompaniesListViewModel::class.java))
            return CompaniesListViewModel(CompaniesRepo(apiHelper)) as T
        else if (modelClass.isAssignableFrom(SingleCompanyViewModel::class.java))
            return SingleCompanyViewModel(CompaniesRepo(apiHelper)) as T
        throw IllegalStateException("Unknown class name")
    }
}