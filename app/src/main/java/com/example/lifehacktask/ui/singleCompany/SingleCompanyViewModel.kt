package com.example.lifehacktask.ui.singleCompany

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.data.repository.CompaniesRepo
import com.example.lifehacktask.utils.Resource
import kotlinx.coroutines.Dispatchers

class SingleCompanyViewModel(private val repo: CompaniesRepo): ViewModel() {
    var company: Company? = null
    fun getCompany(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getCompany(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Unknown error occurred!"))
        }
    }
}