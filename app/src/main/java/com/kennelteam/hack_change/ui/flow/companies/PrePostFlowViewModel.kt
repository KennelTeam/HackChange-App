package com.kennelteam.hack_change.ui.flow.companies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kennelteam.hack_change.PostExtended

class PrePostFlowViewModel : ViewModel() {
    var postsToShow: MutableLiveData<MutableList<PostExtended>> = MutableLiveData<MutableList<PostExtended>>()
}