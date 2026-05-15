package com.gramakhata.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gramakhata.app.data.model.Customer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customers WHERE isSettled = 0 ORDER BY (totalCredit - totalPaid) DESC")
    fun getAllActiveCustomers(): LiveData<List<Customer>>

    @Query("SELECT * FROM customers ORDER BY (totalCredit - totalPaid) DESC")
    fun getAllCustomers(): LiveData<List<Customer>>

    @Query("SELECT * FROM customers WHERE id = :id")
    fun getCustomerById(id: Long): LiveData<Customer>

    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerByIdSync(id: Long): Customer?

    @Query("SELECT * FROM customers WHERE name LIKE '%' || :query || '%' AND isSettled = 0 ORDER BY (totalCredit - totalPaid) DESC")
    fun searchCustomers(query: String): LiveData<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer): Long

    @Update
    suspend fun update(customer: Customer)

    @Delete
    suspend fun delete(customer: Customer)

    @Query("SELECT SUM(totalCredit - totalPaid) FROM customers WHERE isSettled = 0")
    fun getTotalDues(): LiveData<Double?>

    @Query("SELECT COUNT(*) FROM customers WHERE isSettled = 0 AND (totalCredit - totalPaid) > 0")
    fun getActiveDebtorCount(): LiveData<Int>
}
