package com.kennelteam.hack_change.ui.flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlowViewModel : ViewModel() {
    var selectedInstrument: MutableLiveData<Int> = MutableLiveData(-1)
}