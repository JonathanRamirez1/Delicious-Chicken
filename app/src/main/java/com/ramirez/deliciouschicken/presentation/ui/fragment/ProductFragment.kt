package com.ramirez.deliciouschicken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramirez.deliciouschicken.R
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
        launchOrderFragment()
    }

    private fun setUpRecyclerView() {
        productAdapter = ProductAdapter(requireContext()) {
            updateTotal()
        }

        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerView.adapter = productAdapter
    }

   private fun updateTotal() {
        val total = productAdapter.selectedProducts.sumOf { it.price }
        binding.totalTextView.text = "Total: $$total"
    }

    private fun launchOrderFragment() {
        binding.proceedToPaymentButton.setOnClickListener {
            val products = productAdapter.selectedProducts.size
            if (products >= 1) {
                findNavController().navigate(R.id.action_productFragment_to_paymentFragment)
            } else {
                Toast.makeText(requireContext(), "Añade un producto por lo menos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}