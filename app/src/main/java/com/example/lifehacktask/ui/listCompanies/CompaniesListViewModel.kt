package com.example.lifehacktask.ui.listCompanies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.lifehacktask.data.repository.CompaniesRepo
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception

class CompaniesListViewModel(private val repo: CompaniesRepo): ViewModel() {
    fun getCompanies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getCompanies()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Unknown error occurred!"))
        }
    }
}