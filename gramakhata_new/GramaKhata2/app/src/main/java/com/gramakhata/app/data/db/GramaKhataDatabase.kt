package com.gramakhata.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gramakhata.app.data.model.Customer
import com.gramakhata.app.data.model.Transaction

@Database(
    entities = [Customer::class, Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class GramaKhataDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: GramaKhataDatabase? = null

        fun getDatabase(context: Context): GramaKhataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GramaKhataDatabase::class.java,
                    "gramakhata_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
