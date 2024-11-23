package com.aqeel.tobuy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aqeel.tobuy.database.entity.ItemEntity
import com.aqeel.tobuy.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemEntityDao {
    @Query("SELECT * FROM item_entity")
     fun getAll(): Flow< List<ItemEntity>>


     @Transaction
    @Query("SELECT * FROM item_entity")
     fun getAllItemWithCategory():Flow< List<ItemWithCategoryEntity>>

    @Insert(onConflict =OnConflictStrategy.REPLACE )
    suspend  fun insert(vararg item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)
}