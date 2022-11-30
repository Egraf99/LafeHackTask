package com.example.lifehacktask.ui.singleCompany

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lifehacktask.data.api.ApiHelper
import com.example.lifehacktask.data.api.RetrofitBuilder
import com.example.lifehacktask.data.model.Company
import com.example.lifehacktask.databinding.CompanySingleFragmentBinding
import com.example.lifehacktask.ui.base.ViewModelFactory
import com.example.lifehacktask.utils.Status
import kotlin.properties.Delegates

private const val ARG_COMPANY_ID = "argCompanyId"

class SingleCompanyFragment: Fragment() {
    private var _binding: CompanySingleFragmentBinding? = null
    private val binding get() = _binding!!

    private var companyId by Delegates.notNull<Int>()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.companiesService))
        )[SingleCompanyViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        companyId =
            arguments?.getInt(ARG_COMPANY_ID) ?: throw IllegalArgumentException("Not receive id")
        observeCompany(companyId)

    }

    private fun observeCompany(id: Int) {
        viewModel.getCompany(id).observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tryAgainBn.visibility = View.GONE
                    binding.imageView.visibility = View.VISIBLE
                    resource.data?.let { company -> updateUI(company[0]) }
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tryAgainBn.visibility = View.VISIBLE
                    binding.imageView.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tryAgainBn.visibility = View.GONE
                    binding.imageView.visibility = View.GONE
                }
            }
        }
    }

    private fun updateUI(company: Company) {
        binding.companyNameTv.text = company.name
        binding.descriptionTv.text = company.description
        updateViewIfNotBlank(
            binding.phone,
            company.phone
        ) { company.phone.isNotBlank() && company.phone != "0" && company.phone != "null" }
        updateViewIfNotBlank(
            binding.web,
            company.www
        ) { company.www.isNotBlank() && company.www != "0" && company.www != "null" }
        updateViewIfNotBlank(
            binding.location,
            "${company.lat} ${company.long}"
        ) {
            (company.lat.isNotBlank() && company.long.isNotBlank()) &&
                    (company.lat != "0" && company.long != "0") &&
                    (company.lat != "null" && company.long != "null")
        }
    }

    private fun updateViewIfNotBlank(view: TextView, text: String, p: () -> Boolean) {
        if (!p()) view.visibility = View.GONE
        else view.text = text
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CompanySingleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.progressBar.visibility = View.GONE
        binding.tryAgainBn.visibility = View.GONE
        binding.imageView.visibility = View.GONE
        binding.tryAgainBn.setOnClickListener { observeCompany(companyId) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun putId(id: Int): Bundle = Bundle().apply {
            putInt(ARG_COMPANY_ID, id)
        }
    }
}