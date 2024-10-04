package com.aqeel.tobuy

import com.airbnb.epoxy.EpoxyController
import com.aqeel.tobuy.epoxy.models.HeaderEpoxyModel

fun EpoxyController.addHeaderModel(headerText:String){
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)

}