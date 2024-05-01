package com.example.homework12

sealed class State
class Waiting(val lastResult:String? = null) : State()
data object Loading : State(){
    const val LOADING = "Loading..."
}
class Ready(val lastResult:String? = null) : State()
class Result(val value: String) : State()