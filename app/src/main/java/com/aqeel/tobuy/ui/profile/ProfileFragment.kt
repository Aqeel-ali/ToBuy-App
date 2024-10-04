package com.aqeel.tobuy.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aqeel.tobuy.R
import com.aqeel.tobuy.databinding.FragmentProfileBinding
import com.aqeel.tobuy.ui.BaseFragment

class ProfileFragment:BaseFragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!


    private val profileEpoxyController=ProfileEpoxyController(
        onCategoryEmptyStateClicked = ::onCategoryEmptyStateClicked
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileEpoxyRecyclerView.setController(profileEpoxyController)

        sharedViewModel.categoryEntitiesLiveData.observe(viewLifecycleOwner){ categoryList->
            profileEpoxyController.elements=categoryList

        }




    }

    private fun onCategoryEmptyStateClicked(){
       navigateViaGraph(R.id.action_ProfileFragment_to_addCategoryFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}