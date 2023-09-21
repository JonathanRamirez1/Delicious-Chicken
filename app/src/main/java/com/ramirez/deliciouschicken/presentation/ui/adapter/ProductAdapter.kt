package com.ramirez.deliciouschicken.presentation.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ramirez.deliciouschicken.databinding.ItemProductBinding
import com.ramirez.deliciouschicken.domain.model.Product

class ProductAdapter(private val context: Context, private val onProductsUpdated: () -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: List<Product> = listOf()
    var selectedProducts = mutableListOf<Product>()

    init {
        loadProductsFromAssets()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productNameTextView.text = product.name
            binding.productCodeTextView.text = "Código: ${product.code}"
            binding.productQuantityTextView.text = "Cantidad disponible: ${product.quantity}"

            val assets = context.assets
            val inputStream = product.image?.let { assets.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.productImageView.setImageDrawable(drawable)

            binding.productCheckBox.setOnCheckedChangeListener(null)
            binding.productCheckBox.isChecked = selectedProducts.contains(product)
            binding.productCheckBox.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedProducts(product, isChecked)
            }
        }
    }

    private fun loadProductsFromAssets() {
        try {
            val inputStream = context.assets.open("products.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            val gson = Gson()
            products = gson.fromJson(json, Array<Product>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateSelectedProducts(product: Product, isSelected: Boolean) {
        val position = products.indexOf(product)
        if (isSelected) {
            selectedProducts.add(product)
        } else {
            selectedProducts.remove(product)
        }
        notifyItemChanged(position)
        onProductsUpdated()
    }
}
