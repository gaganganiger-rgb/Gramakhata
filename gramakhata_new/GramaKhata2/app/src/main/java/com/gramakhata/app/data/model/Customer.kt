package com.gramakhata.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val mobile: String,
    val photoUri: String? = null,
    val shopName: String = "",
    val totalCredit: Double = 0.0,
    val totalPaid: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdated: Long = System.currentTimeMillis(),
    val isSettled: Boolean = false
) {
    val netDue: Double get() = totalCredit - totalPaid
}
