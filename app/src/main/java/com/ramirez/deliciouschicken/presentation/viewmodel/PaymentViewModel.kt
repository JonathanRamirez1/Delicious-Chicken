package com.ramirez.deliciouschicken.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel: ViewModel() {

    private val _paymentMethod = MutableLiveData<String>()
    val paymentMethod: LiveData<String> get() = _paymentMethod

    data class Product(val price: Double, var quantity: Int) {
        fun reduceQuantity(amount: Int) {
            quantity -= amount
        }
    }

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    fun processPayment(products: List<Product>, amountGiven: Double? = null, cardNumber: String = "", cardExpiry: String = "", cardCVV: String = ""): Boolean {
        if (_paymentMethod.value == "Efectivo") {
            val totalCost = products.sumOf { it.price }
            val change = amountGiven!! - totalCost

            if (change < 0) {
                return false
            }

            products.forEach { product ->
                product.reduceQuantity(1)
            }

        } else if (_paymentMethod.value == "Tarjeta") {
            if (cardNumber.length != 16 || cardExpiry.length != 5 || cardCVV.length != 3) {
                return false
            }
        }
        return true
    }
}