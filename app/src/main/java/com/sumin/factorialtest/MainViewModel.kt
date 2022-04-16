package com.sumin.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = State(isInProgress = true)
        if (value.isNullOrBlank()) {
            _state.value = State(isError = true)
            return
        }
        viewModelScope.launch {
            val number = value.toLong()
            // calculate
            delay(1000)
            _state.value = State(factorial = number.toString())
        }
    }
}
