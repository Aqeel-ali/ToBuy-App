package com.aqeel.tobuy.ui.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.aqeel.tobuy.R
import com.aqeel.tobuy.database.entity.ItemEntity
import com.aqeel.tobuy.databinding.ModelEmptyStateBinding
import com.aqeel.tobuy.databinding.ModelItemEntityBinding
import com.aqeel.tobuy.epoxy.LoadingEpoxyModel
import com.aqeel.tobuy.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
    private val itemEntityInterface:ItemEntityInterface
):EpoxyController() {

    var isLoading:Boolean=true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var elements=ArrayList<ItemEntity>()
        set(value) {
            field=value
            isLoading=false
            requestModelBuild()
        }

    override fun buildModels() {

        if (isLoading){
           LoadingEpoxyModel().id("Loading state").addTo(this)
            return
        }
        else if (elements.isEmpty()){
            EmptyStateEopxyModel().id("empty_state").addTo(this)
            return
        }
        else{
            elements.forEach {item->
            ItemEntityEpoxyModel(item,itemEntityInterface).id(item.id).addTo(this)
            }
        }

    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemEntity,
        val itemEntityInterface: ItemEntityInterface
    ):ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity){
        override fun ModelItemEntityBinding.bind() {
            itemTitle.text=itemEntity.title

            if (itemEntity.description==null){
                itemDescription.isGone=true
            }else {
                itemDescription.isVisible=true
                itemDescription.text = itemEntity.description
            }



            itemPriority.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
            }

           val color = when (itemEntity.priority){
                1->android.R.color.holo_green_light
                2->android.R.color.holo_orange_light
                3->android.R.color.holo_red_light
               else -> android.R.color.holo_purple
            }
            itemPriority.setBackgroundColor(ContextCompat.getColor(root.context,color))

        }
    }
     class EmptyStateEopxyModel :ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state){
        override fun ModelEmptyStateBinding.bind() {
           //nothing to do
        }
    }

}