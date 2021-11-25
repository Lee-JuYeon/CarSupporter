package com.cavss.carsupporter.vm

import android.location.Location
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cavss.carsupporter.backend.retrofit2.RetrofitManager
import com.cavss.carsupporter.model.adblue.AdBlueModel
import com.cavss.carsupporter.repository.AdBlueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AdBlueVM : ViewModel() {
    private val _adBlueList = MutableLiveData<List<AdBlueModel>>()
    private fun setAdBlueList(list : List<AdBlueModel>){
        _adBlueList.value = list.sortedByDescending { it.stock }
    }
    val getAdBlueList : LiveData<List<AdBlueModel>>
        get() = _adBlueList

    private fun getAdBlueListByRetrofit(){
        RetrofitManager.instance.getDefShopList {
            setAdBlueList(list = it)
        }
    }

    private val _text = MutableLiveData<String>("zzzz")
    fun setText(text : String) { _text.postValue(text) }
    val getText : LiveData<String>
        get() = _text

    val location = MutableStateFlow<Location>(getCurrentLocation(0.0, 0.0))
    val addressText = mutableStateOf("")
    var isMapEditable = mutableStateOf(true)
    var timer: CountDownTimer? = null

    fun getCurrentLocation(
        latitude : Double,
        longitude : Double
    ) : Location {
        val initialLocation = Location("")
        initialLocation.latitude = latitude
        initialLocation.longitude = longitude
        return initialLocation
    }

    fun updateLocation(latitude: Double, longitude: Double){
        if(latitude != location.value.latitude) {
            val location = Location("")
            location.latitude = latitude
            location.longitude = longitude
            setLocation(location)
        }
    }

    fun setLocation(loc: Location) {
        location.value = loc
    }



    init {
        getAdBlueListByRetrofit()
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