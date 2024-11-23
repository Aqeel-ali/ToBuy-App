package com.aqeel.tobuy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aqeel.tobuy.database.dao.CategoryEntityDao
import com.aqeel.tobuy.database.dao.ItemEntityDao
import com.aqeel.tobuy.database.entity.CategoryEntity
import com.aqeel.tobuy.database.entity.ItemEntity

@Database(
    entities = [ItemEntity::class,CategoryEntity::class],
    version = 2
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
                ).addMigrations(
MIGRATION_1_2()
                )
                    .build()
                return appDatabase!!
            }
        }



    }
    class MIGRATION_1_2 : Migration(1,2){
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS 'category_entity' ( 'id' TEXT NOT NULL ,'name' TEXT NOT NULL,PRIMARY KEY('id') ) ")
        }
    }


    abstract fun itemEntityDao(): ItemEntityDao
    abstract fun categoryEntityDao(): CategoryEntityDao

}