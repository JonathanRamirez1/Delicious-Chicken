package com.ramirez.deliciouschicken.domain.model

data class Product(
    val code: String? = null,
    val name: String? = null,
    var quantity: Int? = null,
    val image: String? = null,
    val price: Double = 0.0
)
