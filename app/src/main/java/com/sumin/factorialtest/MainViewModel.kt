package com.sumin.factorialtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default + CoroutineName("My coroutine scope"))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        coroutineScope.launch(Dispatchers.Main) {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(result)
            Log.d("MainViewModel", coroutineContext.toString())
        }
    }

    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()
    }

//    private suspend fun factorial(number: Long): String {
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number) {
//                    result = result.multiply(BigInteger.valueOf(i))
//                }
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
