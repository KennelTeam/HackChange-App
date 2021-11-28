package com.kennelteam.hack_change.ui.flow.post_flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostFlowViewModel : ViewModel() {
    var selectedPost: MutableLiveData<Int> = MutableLiveData(-1)
}