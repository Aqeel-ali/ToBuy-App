package com.aqeel.tobuy.ui.add

import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyController
import com.aqeel.tobuy.R
import com.aqeel.tobuy.arch.ToViewBuyModel
import com.aqeel.tobuy.databinding.ModelCategoryItemSelectionBinding
import com.aqeel.tobuy.epoxy.LoadingEpoxyModel
import com.aqeel.tobuy.epoxy.ViewBindingKotlinModel
import com.aqeel.tobuy.getAttrColor

class CategoryViewStateEpoxyController(
    private val onCategorySelected:(String)->Unit
):EpoxyController() {

   var viewState=ToViewBuyModel.CategoryViewState()
       set(value) {
           field=value
           requestModelBuild()
       }

    override fun buildModels() {
        if (viewState.isLoading){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        viewState.itemList.forEach { item ->
            CategoryViewStateItem(item,onCategorySelected).id(item.categoryEntity.id).addTo(this)
        }
    }
    data class CategoryViewStateItem(
        val item:ToViewBuyModel.CategoryViewState.Item,
        private val onCategorySelected: (String) -> Unit
    ):ViewBindingKotlinModel<ModelCategoryItemSelectionBinding>(R.layout.model_category_item_selection){
        override fun ModelCategoryItemSelectionBinding.bind() {
            textView.text=item.categoryEntity.name

            root.setOnClickListener {
                onCategorySelected(item.categoryEntity.id)
            }


            val colorRes=if( item.isSelected) com.google.android.material.R.attr.colorSecondary else com.airbnb.viewmodeladapter.R.attr.colorPrimary
            textView.setTextColor(root.getAttrColor(colorRes))
            root.setStrokeColor(ColorStateList.valueOf(colorRes))
        }
    }

}