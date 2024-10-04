package com.aqeel.tobuy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import com.airbnb.epoxy.EpoxyTouchHelper
import com.aqeel.tobuy.R
import com.aqeel.tobuy.database.entity.ItemEntity
import com.aqeel.tobuy.databinding.FragmentHomeBinding
import com.aqeel.tobuy.ui.BaseFragment

class HomeFragment:BaseFragment(),ItemEntityInterface {

    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        val controller=HomeEpoxyController(this)
        binding.homeEpoxyRecycler.setController(controller)

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner){ itemList->
        controller.elements=itemList as ArrayList<ItemEntity>
        }

     //setup swipe to delete
        EpoxyTouchHelper.initSwiping(binding.homeEpoxyRecycler)
            .left() // Which directions to allow
            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
            .andCallbacks(object: EpoxyTouchHelper.SwipeCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: HomeEpoxyController.ItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                   val itemThatWasRemove =model?.itemEntity?:return
                    sharedViewModel.deleteItem(itemThatWasRemove)
                }
            })


    }



    override fun onBumpPriority(itemEntity: ItemEntity) {
       val currentPriority=itemEntity.priority
        var newPriority=currentPriority+1
        if(newPriority>3){
            newPriority=1
        }
        val updatedItemEntity=itemEntity.copy(priority = newPriority)
        sharedViewModel.updateItem(updatedItemEntity)
    }

    override fun onItemSelected(itemEntity: ItemEntity) {
        val navDirections:NavDirections=HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
        navigateViaGraph(navDirections)
    }

    //    override fun onItemSelected(itemEntity: ItemEntity) {
//        val navDirections=HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
//        navigateViaGraph(navDirections)
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}


