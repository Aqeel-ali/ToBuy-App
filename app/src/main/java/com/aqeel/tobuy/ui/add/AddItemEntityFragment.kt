package com.aqeel.tobuy.ui.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.aqeel.tobuy.R
import com.aqeel.tobuy.database.entity.ItemEntity
import com.aqeel.tobuy.databinding.FragmentAddItemEntityBinding
import com.aqeel.tobuy.ui.BaseFragment
import java.util.UUID

class AddItemEntityFragment:BaseFragment() {
    private var _binding: FragmentAddItemEntityBinding?=null
    private val binding get() = _binding!!

   private val safeArgs:AddItemEntityFragmentArgs by navArgs()

    private val selectedItemEntity:ItemEntity? by lazy {
        sharedViewModel.itemEntitiesLiveData.value?.find {
            it.id==safeArgs.ItemId
        }
    }

    private var isEditMode:Boolean=false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentAddItemEntityBinding.inflate(inflater,container,false)
        return binding.root
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //setup screen if we are in update item
        selectedItemEntity?.let { item ->
            isEditMode=true
            binding.titleEditText.setText(item.title)
            binding.descriptionEditText.setText(item.description)
            when(item.priority){
                1->binding.radioGroup.check(R.id.radioButtonLow)
                2->binding.radioGroup.check(R.id.radioButtonMedium)
                else->binding.radioGroup.check(R.id.radioButtonHigh)
            }
            binding.saveButton.text="Update"
            mainActivity.supportActionBar?.title="Update item"


            if (item.title.contains("[")){
                try {
                    val progress = item.title.substring(startIndex = item.title.indexOf("[")+1, endIndex = item.title.indexOf("]") )
                    binding.quantitySeekBar.setProgress(progress.toInt())
                }catch (e:Exception
                ){
                    //Whoops
                }
            }


        }

        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }

        //seek bar
        binding.quantitySeekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               var currentText=binding.titleEditText.text.toString().trim()

                if (currentText.isEmpty()){
                    return
                }

                val endIndex=currentText.indexOf("[")-1
                val newText=if (endIndex>0){
                    "${currentText.substring(0,endIndex)} [$progress]"
                }else{
                    "$currentText [$progress]"
                }

                val sanitizeText=newText.replace("[1]","")
                binding.titleEditText.setText(sanitizeText)
                binding.titleEditText.setSelection(sanitizeText.length)


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //nothing todo
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //nothing todo
            }
        })

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){isComplete->
            if (isComplete){
                if (isEditMode){
                    navigateUp()
                    return@observe
                }
                navigateUp()
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

        if (isEditMode){
            val itemEntity=selectedItemEntity!!.copy(
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority,
            )
            sharedViewModel.updateItem(itemEntity)
            Toast.makeText(requireActivity(),"Update Item Done",Toast.LENGTH_SHORT).show()
            return

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