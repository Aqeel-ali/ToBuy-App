package com.aqeel.tobuy.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aqeel.tobuy.R
import com.aqeel.tobuy.database.entity.ItemEntity
import com.aqeel.tobuy.databinding.FragmentAddItemEntityBinding
import com.aqeel.tobuy.databinding.FragmentHomeBinding
import com.aqeel.tobuy.ui.BaseFragment
import java.util.UUID

class AddItemEntityFragment:BaseFragment() {
    private var _binding: FragmentAddItemEntityBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAddItemEntityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){isComplete->
            if (isComplete){
            binding.titleEditText.text=null
                binding.titleEditText.requestFocus()
             binding.descriptionEditText.text=null
            binding.radioGroup.check(R.id.radioButtonLow)
                Toast.makeText(requireContext(),"Item Added",Toast.LENGTH_SHORT).show()
            }


        }


    }

    private fun saveItemEntityToDatabase(){
        val itemTitle=binding.titleEditText.text.toString().trim()
        if (itemTitle.isEmpty()){
            binding.titleTextField.error="* Required field"
            return
        }
        binding.titleTextField.error=null

val itemDescription=binding.descriptionEditText.text.toString().trim()
        val itemPriority = when(binding.radioGroup.checkedRadioButtonId){
            R.id.radioButtonLow ->1
            R.id.radioButtonMedium->2
            R.id.radioButtonHigh->3
            else -> 0

        }

        val itemEntity=ItemEntity(
            id=UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAr = System.currentTimeMillis(),
            categoryId = ""//todo update this later when we have category

        )

        sharedViewModel.insertItem(itemEntity)



    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompleteLiveData.postValue(false)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}