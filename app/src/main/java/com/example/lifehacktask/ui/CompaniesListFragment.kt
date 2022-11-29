package com.example.lifehacktask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.lifehacktask.data.Company
import com.example.lifehacktask.databinding.CompaniesListFragmentBinding
import com.example.lifehacktask.databinding.CompanyHolderBinding

private const val TAG = "CLF"

class CompaniesListFragment: Fragment() {
    private var _binding: CompaniesListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompaniesListViewModel by lazy {
        ViewModelProvider(this)[CompaniesListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: work")
        _binding = CompaniesListFragmentBinding.inflate(inflater, container, false)
        binding.companiesRv.layoutManager = LinearLayoutManager(context)
        binding.companiesRv.adapter = CompaniesAdapter(viewModel.getCompanies())
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class CompaniesAdapter(private val companies: List<Company>) :
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
}

class CompanyHolder(private val binding: CompanyHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(company: Company) {
        Log.d(TAG, "bind: $company")
        binding.companyName.text = company.name
    }
}