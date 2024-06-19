package com.dudu.viewmodeltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dudu.common.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : BaseViewModel() {

    private val _testString = MutableLiveData<String>()
    val testString: LiveData<String> = _testString

    fun setData(str: String){
        _testString.postValue(str)
    }

}