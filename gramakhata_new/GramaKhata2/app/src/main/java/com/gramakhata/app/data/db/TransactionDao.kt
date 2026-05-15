package com.gramakhata.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gramakhata.app.data.model.Transaction

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions WHERE customerId = :customerId ORDER BY date DESC")
    fun getTransactionsForCustomer(customerId: Long): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT 50")
    fun getRecentTransactions(): LiveData<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction): Long

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE customerId = :customerId AND type = 'CREDIT'")
    suspend fun getTotalCreditForCustomer(customerId: Long): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE customerId = :customerId AND type = 'PAYMENT'")
    suspend fun getTotalPaymentForCustomer(customerId: Long): Double?

    @Query("SELECT * FROM transactions WHERE date >= :startOfDay AND date <= :endOfDay ORDER BY date DESC")
    fun getTransactionsForDay(startOfDay: Long, endOfDay: Long): LiveData<List<Transaction>>
}
