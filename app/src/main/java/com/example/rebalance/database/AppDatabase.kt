package com.example.rebalance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.rebalance.models.Holding
import com.example.rebalance.models.User
import com.example.rebalance.models.WatchItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Holding::class, WatchItem::class], version = 11, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun holdingDao(): HoldingDao
    abstract fun watchItemDao(): WatchItemDao

    companion object {
        @Volatile
            private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                        )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

