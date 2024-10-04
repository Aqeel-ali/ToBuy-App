package com.aqeel.tobuy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aqeel.tobuy.database.entity.CategoryEntity

import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryEntityDao {
    @Query("SELECT * FROM category_entity")
     fun getAll(): Flow< List<CategoryEntity>>

    @Insert(onConflict =OnConflictStrategy.REPLACE )
    suspend  fun insert(vararg category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)
}