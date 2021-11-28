package com.kennelteam.hack_change.ui.flow.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    val data: MutableLiveData<Int> = MutableLiveData(-1)
}
