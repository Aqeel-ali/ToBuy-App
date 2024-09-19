package com.aqeel.tobuy.ui.home

import com.aqeel.tobuy.database.entity.ItemEntity

interface ItemEntityInterface {

    fun onBumpPriority(itemEntity: ItemEntity)
}