package com.cavss.carsupporter.vm

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cavss.carsupporter.backend.retrofit2.RetrofitManager
import com.cavss.carsupporter.model.adblue.AdBlueModel

class AdBlueVM : ViewModel() {
    private val _adBlueList = MutableLiveData<List<AdBlueModel>>()
    fun setAdBlueList(list : List<AdBlueModel>){
        _adBlueList.value = list
            .filterNot {
                it.stock == 0.0
            }
    }
    val getAdBlueList : LiveData<List<AdBlueModel>>
        get() = _adBlueList

    private val _text = mutableStateOf("")
    fun setText(text : String) {
        _text.value = text
        try {
            val newList =  _adBlueList.value?.filter { it.address.contains(text) } ?: listOf()
            setAdBlueList(list = newList)
            Log.e("mException", "AdBlueVM, text : ${_text}setText : ${newList}")
        }catch (e:Exception){
            Log.e("mException", "AdBlueVM, setText // Exception : ${e.message}")
        }
    }
    val getText : State<String>
        get() = _text

    private val _location = MutableLiveData(mapOf("lati" to 0.0, "long" to 0.0))
    fun setLocation(position : Map<String, Double>) { _location.postValue(position) }
    val getLocation : LiveData<Map<String, Double>>
        get() = _location

    init {
        RetrofitManager.instance.getDefShopList {
            setAdBlueList(list = it)
        }
    }

}

/*

    val adBlueFlow = MutableStateFlow<List<AdBlueModel>>(listOf())
    private fun setInit(){
        viewModelScope.launch {
            kotlin.runCatching { // runCathing for catching the error
                AdBlueRepository.fetchShop()
            }.onSuccess {
                Log.e("mException", "DefVM, setInit, Success")
                adBlueFlow.value = it
            }.onFailure {
                Log.e("mException", "DefVM, setInit, onFailure // Exception : ${it.localizedMessage}")
                adBlueFlow.value = listOf()
            }
        }
    }


 */