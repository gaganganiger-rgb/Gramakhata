package com.gramakhata.app.ui.customers

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gramakhata.app.R
import com.gramakhata.app.data.model.Customer
import com.gramakhata.app.databinding.ItemCustomerBinding
import java.io.File

class CustomerAdapter(
    private val onClick: (Customer) -> Unit
) : ListAdapter<Customer, CustomerAdapter.CustomerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CustomerViewHolder(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(customer: Customer) {
            binding.tvCustomerName.text = customer.name
            binding.tvMobile.text = customer.mobile
            binding.tvNetDue.text = "₹${String.format("%,.2f", customer.netDue)}"

            // Avatar initials
            binding.tvInitials.text = customer.name.take(1).uppercase()

            // Photo
            if (!customer.photoUri.isNullOrBlank()) {
                try {
                    val file = File(customer.photoUri)
                    if (file.exists()) {
                        binding.ivAvatar.setImageURI(Uri.fromFile(file))
                        binding.tvInitials.visibility = android.view.View.GONE
                        binding.ivAvatar.visibility = android.view.View.VISIBLE
                    }
                } catch (e: Exception) {
                    binding.tvInitials.visibility = android.view.View.VISIBLE
                }
            }

            // Color-code due badge
            val context = binding.root.context
            when {
                customer.netDue <= 0 -> {
                    binding.tvNetDue.setTextColor(context.getColor(R.color.green_paid))
                    binding.cardDueBadge.setCardBackgroundColor(context.getColor(R.color.green_paid_bg))
                }
                customer.netDue < 500 -> {
                    binding.tvNetDue.setTextColor(context.getColor(R.color.amber_mid))
                    binding.cardDueBadge.setCardBackgroundColor(context.getColor(R.color.amber_mid_bg))
                }
                else -> {
                    binding.tvNetDue.setTextColor(context.getColor(R.color.red_high))
                    binding.cardDueBadge.setCardBackgroundColor(context.getColor(R.color.red_high_bg))
                }
            }

            binding.root.setOnClickListener { onClick(customer) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Customer, newItem: Customer) = oldItem == newItem
        }
    }
}
