package com.gramakhata.app.ui.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gramakhata.app.R
import com.gramakhata.app.data.model.Transaction
import com.gramakhata.app.data.model.TransactionType
import com.gramakhata.app.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(
    private val onDelete: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

        fun bind(transaction: Transaction) {
            val context = binding.root.context
            val isCredit = transaction.type == TransactionType.CREDIT

            binding.tvAmount.text = "${if (isCredit) "+" else "-"} ₹${String.format("%,.2f", transaction.amount)}"
            binding.tvNote.text = transaction.note.ifBlank { if (isCredit) "Credit given" else "Payment received" }
            binding.tvDate.text = dateFormat.format(Date(transaction.date))
            binding.tvTransactionType.text = if (isCredit) "CREDIT" else "PAID"

            if (isCredit) {
                binding.tvAmount.setTextColor(context.getColor(R.color.red_high))
                binding.tvTransactionType.setTextColor(context.getColor(R.color.red_high))
                binding.tvTransactionType.backgroundTintList = context.getColorStateList(R.color.red_high_bg)
                binding.ivTypeIcon.setImageResource(R.drawable.ic_credit)
                binding.ivTypeIcon.imageTintList = context.getColorStateList(R.color.red_high)
            } else {
                binding.tvAmount.setTextColor(context.getColor(R.color.green_paid))
                binding.tvTransactionType.setTextColor(context.getColor(R.color.green_paid))
                binding.tvTransactionType.backgroundTintList = context.getColorStateList(R.color.green_paid_bg)
                binding.ivTypeIcon.setImageResource(R.drawable.ic_payment)
                binding.ivTypeIcon.imageTintList = context.getColorStateList(R.color.green_paid)
            }

            binding.btnDelete.setOnClickListener { onDelete(transaction) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
        }
    }
}
