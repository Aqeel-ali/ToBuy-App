package com.aqeel.tobuy.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqeel.tobuy.database.AppDatabase
import com.aqeel.tobuy.database.entity.CategoryEntity
import com.aqeel.tobuy.database.entity.ItemEntity
import kotlinx.coroutines.launch

class ToBuyModel() : ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

    val categoryEntitiesLiveData =MutableLiveData<List<CategoryEntity>>()


    val transactionCompleteLiveData =MutableLiveData<Boolean>()

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        viewModelScope.launch {

            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)
            }
        }

            viewModelScope.launch {
            repository.getAllCategories().collect{ categories->
                categoryEntitiesLiveData.postValue(categories)
            }

        }


    }


    //region ItemEntity
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)
            transactionCompleteLiveData.postValue(true)

        }
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)
            transactionCompleteLiveData.postValue(true)
        }
    }
    //endregion ItemEntity

    //region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)
            transactionCompleteLiveData.postValue(true)

        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(categoryEntity)
            transactionCompleteLiveData.postValue(true)
        }
    }
    //endregion CategoryEntity




}