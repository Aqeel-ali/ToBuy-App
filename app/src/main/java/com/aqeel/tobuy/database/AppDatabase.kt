package com.aqeel.tobuy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aqeel.tobuy.database.dao.ItemEntityDao
import com.aqeel.tobuy.database.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) {
                return appDatabase!!
            } else {
                appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "to_buy_database"
                ).build()
                return appDatabase!!
            }
        }

    }


    abstract fun itemEntityDao(): ItemEntityDao
}