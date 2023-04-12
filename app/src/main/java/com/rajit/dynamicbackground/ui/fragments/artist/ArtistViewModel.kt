package com.rajit.dynamicbackground.ui.fragments.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajit.dynamicbackground.data.model.Result
import com.rajit.dynamicbackground.data.repository.ArtistRepository
import com.rajit.dynamicbackground.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: ArtistRepository
): ViewModel(){

    private val _list = MutableLiveData<Resource<Result>>()
    val list: LiveData<Resource<Result>> get() = _list

    init {
        // getting the list as soon as the viewModel is initialized
        getList()
    }

    private fun getList() = viewModelScope.launch {
        _list.postValue(Resource.loading(null))

        repository.getList().let {
            if(it.isSuccessful) {
                _list.postValue(Resource.success(it.body()))
            } else {
                _list.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}