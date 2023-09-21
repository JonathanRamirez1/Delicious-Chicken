package com.ramirez.deliciouschicken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramirez.deliciouschicken.databinding.FragmentProductBinding
import com.ramirez.deliciouschicken.presentation.ui.adapter.ProductAdapter

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        productAdapter = ProductAdapter(requireContext()) {
            updateTotal()
        }

        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerView.adapter = productAdapter
    }

    private fun updateTotal() {
        val totalItems = productAdapter.selectedProducts.size
        binding.totalTextView.text = "Total: $totalItems"
    }

}