package com.aqeel.tobuy.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqeel.tobuy.database.AppDatabase
import com.aqeel.tobuy.database.entity.ItemEntity
import kotlinx.coroutines.launch

class ToBuyModel() : ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitesLiveData = MutableLiveData<List<ItemEntity>>()


    val transactionCompleteLiveData =MutableLiveData<Boolean>()

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitesLiveData.postValue(items)
            }

        }


    }

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
        }
    }
}