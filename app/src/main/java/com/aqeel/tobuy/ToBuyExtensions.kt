package com.aqeel.tobuy

import android.view.View
import androidx.annotation.ColorInt
import com.airbnb.epoxy.EpoxyController
import com.aqeel.tobuy.epoxy.models.HeaderEpoxyModel
import com.google.android.material.color.MaterialColors

fun EpoxyController.addHeaderModel(headerText:String){
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)

}

@ColorInt
 fun View.getAttrColor(attrResId:Int):Int{
    return MaterialColors.getColor(this,attrResId)
}