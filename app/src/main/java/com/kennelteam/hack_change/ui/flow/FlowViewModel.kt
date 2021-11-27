package com.kennelteam.hack_change.ui.flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is flow Fragment"
    }
    val text: LiveData<String> = _text
}