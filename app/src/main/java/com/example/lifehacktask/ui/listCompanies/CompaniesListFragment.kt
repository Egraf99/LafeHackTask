package com.example.lifehacktask.ui.listCompanies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifehacktask.R
import com.example.lifehacktask.data.api.ApiHelper
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.data.api.RetrofitBuilder
import com.example.lifehacktask.databinding.CompaniesListFragmentBinding
import com.example.lifehacktask.databinding.CompanyHolderBinding
import com.example.lifehacktask.ui.base.ViewModelFactory
import com.example.lifehacktask.ui.company_interface.OnCompanyHolderClick
import com.example.lifehacktask.ui.singleCompany.SingleCompanyFragment
import com.example.lifehacktask.utils.Status

private const val TAG = "CLF"

class CompaniesListFragment : Fragment(), OnCompanyHolderClick {
    private var _binding: CompaniesListFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter = CompaniesAdapter(this, arrayListOf())

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
        observeCompanies()
    }

    private fun observeCompanies() {
        viewModel.getCompanies().observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.companiesRv.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.tryAgainBn.visibility = View.GONE
                    resource.data?.let { companies -> retrieveList(companies) }
                }
                Status.ERROR -> {
                    binding.companiesRv.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.tryAgainBn.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.companiesRv.visibility = View.GONE
                    binding.tryAgainBn.visibility = View.GONE
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

    override fun onStart() {
        super.onStart()
        binding.progressBar.visibility = View.GONE
        binding.tryAgainBn.visibility = View.GONE
        binding.tryAgainBn.setOnClickListener { observeCompanies() }
    }

    override fun onClick(id: Int) {
        val bundle = SingleCompanyFragment.putId(id)
        findNavController().navigate(R.id.action_fromList_toSingle, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class CompaniesAdapter(
    private val listener: OnCompanyHolderClick,
    private val companies: ArrayList<Company>
) :
    RecyclerView.Adapter<CompanyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder =
        CompanyHolder(
            CompanyHolderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
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

class CompanyHolder(
    private val binding: CompanyHolderBinding,
    private val listener: OnCompanyHolderClick
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
    }

    private var companyId = 0

    fun bind(company: Company) {
        companyId = company.id
        binding.companyName.text = company.name
    }

    override fun onClick(v: View?) {
        listener.onClick(companyId)
    }
}