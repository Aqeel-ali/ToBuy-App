package com.aqeel.tobuy.arch

import com.aqeel.tobuy.database.AppDatabase
import com.aqeel.tobuy.database.entity.CategoryEntity
import com.aqeel.tobuy.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {


    //region ItemEntity
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


    //endregion ItemEntity

    //region CategoryEntity
    suspend fun insertCategory(categoryEntity: CategoryEntity){
        appDatabase.categoryEntityDao().insert(categoryEntity)
    }

    suspend fun deleteCategory(categoryEntity: CategoryEntity){
        appDatabase.categoryEntityDao().delete(categoryEntity)
    }

      fun getAllCategories(): Flow<List<CategoryEntity>> {
        return appDatabase.categoryEntityDao().getAll()
    }

    suspend fun updateCategory(categoryEntity: CategoryEntity){
        appDatabase.categoryEntityDao().updateCategory(categoryEntity)
    }

    //endregion ItemEntity


}