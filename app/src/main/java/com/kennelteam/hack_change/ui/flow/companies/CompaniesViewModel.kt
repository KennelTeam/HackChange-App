package com.kennelteam.hack_change.ui.flow.companies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompaniesViewModel : ViewModel() {
    var selectedTopic: MutableLiveData<Int> = MutableLiveData<Int>(0)
}