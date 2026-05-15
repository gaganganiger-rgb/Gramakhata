package com.gramakhata.app.ui.customers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gramakhata.app.R
import com.gramakhata.app.data.model.Transaction
import com.gramakhata.app.databinding.ActivityCustomerDetailBinding
import com.gramakhata.app.databinding.DialogAddTransactionBinding
import com.gramakhata.app.viewmodel.CustomerDetailViewModel
import java.io.File

class CustomerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerDetailBinding
    private val viewModel: CustomerDetailViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val customerId = intent.getLongExtra("customer_id", -1L)
        if (customerId == -1L) { finish(); return }

        viewModel.setCustomerId(customerId)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter { transaction ->
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete Entry?")
                .setMessage("This will remove the entry and recalculate balance.")
                .setPositiveButton("Delete") { _, _ -> viewModel.deleteTransaction(transaction) }
                .setNegativeButton("Cancel", null)
                .show()
        }
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@CustomerDetailActivity)
            adapter = transactionAdapter
        }
    }

    private fun setupObservers() {
        viewModel.customer.observe(this) { customer ->
            supportActionBar?.title = customer.name
            binding.tvCustomerName.text = customer.name
            binding.tvMobile.text = customer.mobile
            binding.tvNetDue.text = "₹${String.format("%,.2f", customer.netDue)}"
            binding.tvTotalCredit.text = "₹${String.format("%,.2f", customer.totalCredit)}"
            binding.tvTotalPaid.text = "₹${String.format("%,.2f", customer.totalPaid)}"

            // Color net due
            when {
                customer.netDue <= 0 -> binding.tvNetDue.setTextColor(getColor(R.color.green_paid))
                customer.netDue < 500 -> binding.tvNetDue.setTextColor(getColor(R.color.amber_mid))
                else -> binding.tvNetDue.setTextColor(getColor(R.color.red_high))
            }

            // Photo
            if (!customer.photoUri.isNullOrBlank()) {
                try {
                    val file = File(customer.photoUri)
                    if (file.exists()) binding.ivCustomerPhoto.setImageURI(Uri.fromFile(file))
                } catch (e: Exception) { /* use default */ }
            }
            binding.tvInitials.text = customer.name.take(1).uppercase()
        }

        viewModel.transactions.observe(this) { transactions ->
            transactionAdapter.submitList(transactions)
            binding.tvNoTransactions.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.btnCredit.setOnClickListener {
            showTransactionDialog(isCredit = true)
        }

        binding.btnPayment.setOnClickListener {
            showTransactionDialog(isCredit = false)
        }

        binding.btnRemind.setOnClickListener {
            sendReminder()
        }
    }

    private fun showTransactionDialog(isCredit: Boolean) {
        val dialogBinding = DialogAddTransactionBinding.inflate(LayoutInflater.from(this))
        dialogBinding.tvDialogTitle.text = if (isCredit) "Add Credit (Gave Goods)" else "Add Payment (Received Money)"
        dialogBinding.tvDialogTitle.setTextColor(getColor(if (isCredit) R.color.red_high else R.color.green_paid))
        dialogBinding.btnDialogIcon.setImageResource(if (isCredit) R.drawable.ic_credit else R.drawable.ic_payment)

        MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .setPositiveButton(if (isCredit) "Add Credit" else "Mark Paid") { _, _ ->
                val amountStr = dialogBinding.etAmount.text.toString()
                val note = dialogBinding.etNote.text.toString()
                if (amountStr.isBlank()) {
                    Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val amount = amountStr.toDoubleOrNull() ?: return@setPositiveButton
                if (isCredit) viewModel.addCredit(amount, note)
                else viewModel.addPayment(amount, note)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun sendReminder() {
        val customer = viewModel.customer.value ?: return
        if (customer.netDue <= 0) {
            Toast.makeText(this, "No dues pending!", Toast.LENGTH_SHORT).show()
            return
        }
        val message = "Namaskara ${customer.name},\n\nYour outstanding due is *₹${String.format("%,.2f", customer.netDue)}*.\n\nPlease clear at your earliest convenience.\n\nThank you 🙏\n\n_Sent via Grama-Khata_"

        // Try WhatsApp first, fallback to SMS
        try {
            val waIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.whatsapp")
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(waIntent)
        } catch (e: Exception) {
            val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${customer.mobile}")).apply {
                putExtra("sms_body", message)
            }
            startActivity(smsIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
