package com.example.lifehacktask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehacktask.data.api.ApiHelper
import com.example.lifehacktask.data.repository.CompaniesRepo
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.data.api.CompaniesService
import com.example.lifehacktask.data.api.RetrofitBuilder
import com.example.lifehacktask.databinding.CompaniesListFragmentBinding
import com.example.lifehacktask.databinding.CompanyHolderBinding
import com.example.lifehacktask.ui.base.ViewModelFactory
import com.example.lifehacktask.utils.Status
import retrofit2.Call
import retrofit2.Response

private const val TAG = "CLF"

class CompaniesListFragment: Fragment() {
    private var _binding: CompaniesListFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter = CompaniesAdapter(arrayListOf())

    private val viewModel: CompaniesListViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.companiesService))
        )[CompaniesListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getCompanies().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.companiesRv.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { companies -> retrieveList(companies) }
                }
                Status.ERROR -> {
                    binding.companiesRv.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "setupObservers: ${resource.message}")
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.companiesRv.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun retrieveList(companies: List<Company>) {
        adapter.apply {
            addCompanies(companies)
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CompaniesListFragmentBinding.inflate(inflater, container, false)
        binding.companiesRv.layoutManager = LinearLayoutManager(context)
        binding.companiesRv.adapter = adapter
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class CompaniesAdapter(private val companies: ArrayList<Company>) :
    RecyclerView.Adapter<CompanyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder =
        CompanyHolder(
            CompanyHolderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CompanyHolder, position: Int) =
        holder.bind(companies[position])

    override fun getItemCount(): Int = companies.size

    fun addCompanies(companies: List<Company>) {
        this.companies.apply {
            clear()
            addAll(companies)
        }
    }
}

class CompanyHolder(private val binding: CompanyHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(company: Company) {
        Log.d(TAG, "bind: $company")
        binding.companyName.text = company.name
    }
}