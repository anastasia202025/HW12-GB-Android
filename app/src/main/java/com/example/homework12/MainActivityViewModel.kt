package com.example.homework12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.homework12.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class  MainActivityViewModel : ViewModel() {

    private var cacheResult:String? = null
    private var _state:MutableLiveData<State> = MutableLiveData(Waiting(cacheResult))
    val state:LiveData<State> get() = _state

    private var results = listOf(
        """We couldn't find anything about "%s""""
        , """"%s" - Not found!"""
        , "Connection error!"
    )



    private val scope = viewModelScope
    fun loading(request: String) {
        scope.launch {
            _state.value = Loading
            delay(5000)
            val result =  String.format(
                results[Random.nextInt(results.size)]
                ,request
            )
            cacheResult = result
            _state.value = Result(result)
        }
    }

    fun setNumberOfLettersInRequest(value:Int){
        if (_state.value !is Loading) {
            _state.value = if (value < 3) Waiting(cacheResult) else Ready(cacheResult)
        }
    }

}