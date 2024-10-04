package com.aqeel.tobuy.epoxy.models

import com.aqeel.tobuy.R
import com.aqeel.tobuy.databinding.ModelHeaderItemBinding
import com.aqeel.tobuy.epoxy.ViewBindingKotlinModel

data class HeaderEpoxyModel(val title:String): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item){
    override fun ModelHeaderItemBinding.bind() {
        textView.text=title
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }

}