package com.aqeel.tobuy.ui.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.aqeel.tobuy.R
import com.aqeel.tobuy.addHeaderModel
import com.aqeel.tobuy.arch.ToBuyViewModel
import com.aqeel.tobuy.database.entity.ItemWithCategoryEntity
import com.aqeel.tobuy.databinding.ModelEmptyStateBinding
import com.aqeel.tobuy.databinding.ModelItemEntityBinding
import com.aqeel.tobuy.epoxy.LoadingEpoxyModel
import com.aqeel.tobuy.epoxy.ViewBindingKotlinModel


class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
) : EpoxyController() {

    var viewState :ToBuyViewModel.HomeViewState= ToBuyViewModel.HomeViewState(isLoading=true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        if (viewState.isLoading) {
            LoadingEpoxyModel().id("Loading state").addTo(this)
            return
        } else if (viewState.dataList.isEmpty()) {
            EmptyStateEopxyModel().id("empty_state").addTo(this)
            return
        }

//            var currentpriority: Int = -1
//            viewState.dataList.sortedByDescending {
//                it.itemEntity.priority
//            }.forEach { item ->
//                if (item.itemEntity.priority != currentpriority) {
//                    currentpriority = item.itemEntity.priority
//                    addHeaderModel(getHeaderTextFromPriority(currentpriority))
//                }
//                ItemEntityEpoxyModel(item, itemEntityInterface).id(item.itemEntity.id).addTo(this)
//
//        }

        viewState.dataList.forEach { dataItem ->
            if (dataItem.isHeader) {
                addHeaderModel(dataItem.data as String)
                return@forEach
            }

            val itemWithCategoryEntity = dataItem.data as ItemWithCategoryEntity
            ItemEntityEpoxyModel(itemWithCategoryEntity, itemEntityInterface)
                .id(itemWithCategoryEntity.itemEntity.id)
                .addTo(this)
        }

    }



    data class ItemEntityEpoxyModel(
        val item: ItemWithCategoryEntity,
        val itemEntityInterface: ItemEntityInterface
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {
        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = item.itemEntity.title

            if (item.itemEntity.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = item.itemEntity.description
            }



            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(item.itemEntity)
            }

            val color = when (item.itemEntity.priority) {
                1 -> android.R.color.holo_green_light
                2 -> android.R.color.holo_orange_light
                3 -> android.R.color.holo_red_light
                else -> android.R.color.holo_purple
            }
            priorityTextView.setBackgroundColor(ContextCompat.getColor(root.context, color))

            categoryNameTextView.text=item.categoryEntity?.name

            root.setOnClickListener {
                itemEntityInterface.onItemSelected(item.itemEntity)
            }


        }

    }

    class EmptyStateEopxyModel :
        ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {
        override fun ModelEmptyStateBinding.bind() {
            //nothing to do
        }
    }


}