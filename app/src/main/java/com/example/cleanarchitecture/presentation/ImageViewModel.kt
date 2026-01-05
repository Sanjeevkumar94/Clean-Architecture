package com.example.cleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.data.model.Hit
import com.example.cleanarchitecture.domain.model.DomainModel
import com.example.cleanarchitecture.domain.useCases.GetImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.Query

class ImageViewModel:ViewModel() {

    private val useCase:GetImagesUseCase by lazy {
        GetImagesUseCase()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState

    val _query = MutableStateFlow("")

    fun updateQuery(query: String){
        _query.value = query
    }

    init {
        viewModelScope.launch {
            _query.filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .debounce(1000)
                .collectLatest { query->
                    getImage(query)
                }
        }
    }

    private fun getImage(query: String) {

        useCase(query).onStart {
            _uiState.update { UiState(isLoading = true) }
        } .onEach {  result ->
            if(result.isSuccess){
                _uiState.update { UiState(data = result.getOrThrow()) }
            } else{
                _uiState.update { UiState(error = result.exceptionOrNull()?.message.toString()) }
            }
        }.catch { error->
            _uiState.update { UiState(error = error.message.toString()) }

        }.launchIn(viewModelScope)
    }

}


data class UiState(
    val isLoading:Boolean=false,
    val error:String="",
    val data:List<DomainModel>?=null
)