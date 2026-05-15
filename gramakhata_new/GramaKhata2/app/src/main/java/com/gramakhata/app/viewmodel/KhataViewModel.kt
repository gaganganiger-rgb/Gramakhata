package com.gramakhata.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gramakhata.app.data.model.Customer
import com.gramakhata.app.data.model.Transaction
import com.gramakhata.app.data.model.TransactionType
import com.gramakhata.app.data.repository.KhataRepository
import kotlinx.coroutines.launch

class KhataViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = KhataRepository(application)

    val allActiveCustomers: LiveData<List<Customer>> = repository.getAllActiveCustomers()
    val totalDues: LiveData<Double?> = repository.getTotalDues()
    val activeDebtorCount: LiveData<Int> = repository.getActiveDebtorCount()

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery

    val displayedCustomers: LiveData<List<Customer>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) repository.getAllActiveCustomers()
        else repository.searchCustomers(query)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addCustomer(customer: Customer, onResult: (Long) -> Unit) = viewModelScope.launch {
        val id = repository.addCustomer(customer)
        onResult(id)
    }

    fun updateCustomer(customer: Customer) = viewModelScope.launch {
        repository.updateCustomer(customer)
    }

    fun deleteCustomer(customer: Customer) = viewModelScope.launch {
        repository.deleteCustomer(customer)
    }

    fun settleCustomer(customer: Customer) = viewModelScope.launch {
        repository.updateCustomer(customer.copy(isSettled = true))
    }
}

class CustomerDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = KhataRepository(application)

    private val _customerId = MutableLiveData<Long>()

    val customer: LiveData<Customer> = _customerId.switchMap { id ->
        repository.getCustomerById(id)
    }

    val transactions: LiveData<List<Transaction>> = _customerId.switchMap { id ->
        repository.getTransactionsForCustomer(id)
    }

    fun setCustomerId(id: Long) {
        _customerId.value = id
    }

    fun addCredit(amount: Double, note: String) = viewModelScope.launch {
        val customerId = _customerId.value ?: return@launch
        repository.addTransaction(Transaction(
            customerId = customerId,
            type = TransactionType.CREDIT,
            amount = amount,
            note = note
        ))
    }

    fun addPayment(amount: Double, note: String) = viewModelScope.launch {
        val customerId = _customerId.value ?: return@launch
        repository.addTransaction(Transaction(
            customerId = customerId,
            type = TransactionType.PAYMENT,
            amount = amount,
            note = note
        ))
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.deleteTransaction(transaction)
    }
}
