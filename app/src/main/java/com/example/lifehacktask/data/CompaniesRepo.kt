package com.example.lifehacktask.data

class CompaniesRepo {
    fun getCompanies(): List<Company> = listOf(
        Company(id = 1, name = "Test 1"),
        Company(id = 2, name = "Test 2"),
        Company(id = 3, name = "Test 3"),
        Company(id = 4, name = "Test 4"),
    )

    companion object {
        private var INSTANCE: CompaniesRepo? = null
        fun initialize() {
            if (INSTANCE == null) INSTANCE = CompaniesRepo()
        }

        fun get(): CompaniesRepo =
            INSTANCE ?: throw IllegalStateException("CompaniesRepo should be initialized")
    }
}