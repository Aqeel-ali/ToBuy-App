package com.aqeel.tobuy.ui.profile

import com.airbnb.epoxy.EpoxyController
import com.aqeel.tobuy.R
import com.aqeel.tobuy.addHeaderModel
import com.aqeel.tobuy.database.entity.CategoryEntity
import com.aqeel.tobuy.databinding.ModelCategoryBinding
import com.aqeel.tobuy.databinding.ModelEmptyButtonBinding
import com.aqeel.tobuy.databinding.ModelEmptyStateBinding
import com.aqeel.tobuy.epoxy.ViewBindingKotlinModel

class ProfileEpoxyController(
    private val onCategoryEmptyStateClicked:()->Unit
):EpoxyController() {

    var elements:List<CategoryEntity> = emptyList()
        set(value) {
            field=value
            requestModelBuild()
        }

    override fun buildModels() {

        addHeaderModel("Categories")

        elements.forEach { item ->
            CategoryEpoxyModel(item).id(item.id).addTo(this)
        }
        EmptyButtonEpoxyModel("Add Category",onCategoryEmptyStateClicked).id("add_item").addTo(this)


    }

    data class CategoryEpoxyModel(
        val category: CategoryEntity
    ):ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category){
        override fun ModelCategoryBinding.bind() {
            textView.text=category.name
        }
    }

    data class EmptyButtonEpoxyModel(
        val buttonText:String,
        val onClicked:()->Unit
    ):ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button){
        override fun ModelEmptyButtonBinding.bind() {
            button.setText(buttonText)

            button.setOnClickListener {
                onClicked()
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }


}