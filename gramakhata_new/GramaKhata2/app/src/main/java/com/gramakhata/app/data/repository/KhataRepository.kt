package com.gramakhata.app.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gramakhata.app.data.db.GramaKhataDatabase
import com.gramakhata.app.data.model.Customer
import com.gramakhata.app.data.model.Transaction
import com.gramakhata.app.data.model.TransactionType

class KhataRepository(context: Context) {

    private val db = GramaKhataDatabase.getDatabase(context)
    private val customerDao = db.customerDao()
    private val transactionDao = db.transactionDao()

    // Customer operations
    fun getAllActiveCustomers(): LiveData<List<Customer>> = customerDao.getAllActiveCustomers()
    fun getAllCustomers(): LiveData<List<Customer>> = customerDao.getAllCustomers()
    fun getCustomerById(id: Long): LiveData<Customer> = customerDao.getCustomerById(id)
    fun searchCustomers(query: String): LiveData<List<Customer>> = customerDao.searchCustomers(query)
    fun getTotalDues(): LiveData<Double?> = customerDao.getTotalDues()
    fun getActiveDebtorCount(): LiveData<Int> = customerDao.getActiveDebtorCount()

    suspend fun addCustomer(customer: Customer): Long = customerDao.insert(customer)
    suspend fun updateCustomer(customer: Customer) = customerDao.update(customer)
    suspend fun deleteCustomer(customer: Customer) = customerDao.delete(customer)

    // Transaction operations
    fun getTransactionsForCustomer(customerId: Long): LiveData<List<Transaction>> =
        transactionDao.getTransactionsForCustomer(customerId)

    fun getRecentTransactions(): LiveData<List<Transaction>> =
        transactionDao.getRecentTransactions()

    suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
        // Recalculate customer balance
        val totalCredit = transactionDao.getTotalCreditForCustomer(transaction.customerId) ?: 0.0
        val totalPaid = transactionDao.getTotalPaymentForCustomer(transaction.customerId) ?: 0.0
        val customer = customerDao.getCustomerByIdSync(transaction.customerId)
        customer?.let {
            customerDao.update(it.copy(
                totalCredit = totalCredit,
                totalPaid = totalPaid,
                lastUpdated = System.currentTimeMillis()
            ))
        }
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
        val totalCredit = transactionDao.getTotalCreditForCustomer(transaction.customerId) ?: 0.0
        val totalPaid = transactionDao.getTotalPaymentForCustomer(transaction.customerId) ?: 0.0
        val customer = customerDao.getCustomerByIdSync(transaction.customerId)
        customer?.let {
            customerDao.update(it.copy(
                totalCredit = totalCredit,
                totalPaid = totalPaid,
                lastUpdated = System.currentTimeMillis()
            ))
        }
    }
}
