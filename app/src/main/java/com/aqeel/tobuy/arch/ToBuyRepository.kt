package com.aqeel.tobuy.arch

import com.aqeel.tobuy.database.AppDatabase
import com.aqeel.tobuy.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {
    suspend fun insertItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().delete(itemEntity)
    }

      fun getAllItems(): Flow<List<ItemEntity>> {
        return appDatabase.itemEntityDao().getAll()
    }

    suspend fun updateItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().updateItem(itemEntity)
    }


}