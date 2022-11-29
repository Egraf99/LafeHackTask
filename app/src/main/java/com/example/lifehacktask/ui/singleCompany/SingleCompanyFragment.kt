package com.example.lifehacktask.ui.singleCompany

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifehacktask.databinding.CompanySingleFragmentBinding

private const val ARG_COMPANY_ID = "argCompanyId"
private const val TAG = "SCF"

class SingleCompanyFragment: Fragment() {
    private var _binding: CompanySingleFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getInt(ARG_COMPANY_ID) ?: 0
        Log.d(TAG, "onCreate: $id")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CompanySingleFragmentBinding.inflate(inflater, container, false)
        return binding.root
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