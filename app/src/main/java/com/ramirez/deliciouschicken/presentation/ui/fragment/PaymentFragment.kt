package com.ramirez.deliciouschicken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ramirez.deliciouschicken.R
import com.ramirez.deliciouschicken.databinding.FragmentPaymentBinding
import com.ramirez.deliciouschicken.domain.model.Product
import com.ramirez.deliciouschicken.presentation.viewmodel.PaymentViewModel


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private val paymentViewModel: PaymentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        resetStateEditText()
    }

    private fun setupObservers() {
        paymentViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            when (method) {
                "Efectivo" -> {
                    binding.cardView.visibility = View.GONE
                }
                "Tarjeta" -> {
                    binding.cardView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupListeners() {
        binding.radioButtonCash.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) paymentViewModel.setPaymentMethod("Efectivo")
        }

        binding.radioButtonTarjeta.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) paymentViewModel.setPaymentMethod("Tarjeta")
        }

        binding.confirmPaymentButton.setOnClickListener {
            val productsToBuy: List<PaymentViewModel.Product> = listOf()
            val cardNumber = binding.cardNumber.text.toString()
            val cardExpiry = binding.cardExpiry.text.toString()
            val cardCVV = binding.cardCVV.text.toString()

            if (paymentViewModel.paymentMethod.value == "Tarjeta" && !isValidFields()) {
                return@setOnClickListener
            }
            val totalCost = productsToBuy.sumOf { it.price * it.quantity }

            val amountGiven = if (productsToBuy.isNotEmpty()) totalCost else null
            val isPaymentSuccessful = paymentViewModel.processPayment(productsToBuy, amountGiven, cardNumber, cardExpiry, cardCVV)
            if (isPaymentSuccessful) {
                Toast.makeText(requireContext(), "Pago exitoso!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_paymentFragment_to_productFragment)
            } else {
                Toast.makeText(requireContext(), "Hubo un error en el pago", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidFields(): Boolean {
        var isValid = true
        binding.apply {
            val cardNumber = cardNumber.text.toString()
            val cardExpiry = cardExpiry.text.toString()
            val cardCVV = cardCVV.text.toString()
            if (cardNumber.length != 16) {
                cardNumberInput.error = "Tarjeta debe tener 16 digitos"
                isValid = false
            }
            if (cardExpiry.length != 5) {
                cardExpiryInput.error = "fecha debe tener mes y año"
                isValid = false
            }
            if (cardCVV.length != 3) {
                cardCVVInput.error = "CVV debe tener 3 digitos"
                isValid = false
            }
        }
        return isValid
    }

    private fun resetStateEditText() {
        binding.apply {
            cardNumber.addTextChangedListener {
                cardNumberInput.error = null
            }
            cardExpiry.addTextChangedListener {
                if (it?.length == 2 && !it.contains("/")) {
                    cardExpiry.setText("$it/")
                    cardExpiry.setSelection(3)
                }
                cardExpiryInput.error = null
            }
            cardCVV.addTextChangedListener {
                cardCVVInput.error = null
            }
        }
    }
}