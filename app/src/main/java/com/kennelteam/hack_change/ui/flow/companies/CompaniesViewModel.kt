package com.kennelteam.hack_change.ui.flow.companies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompaniesViewModel : ViewModel() {
    var selectedTopic: MutableLiveData<Int> = MutableLiveData(-1)
}