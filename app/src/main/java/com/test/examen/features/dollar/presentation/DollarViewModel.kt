package com.test.examen.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.examen.features.dollar.domain.model.DollarModel
import com.test.examen.features.dollar.domain.usecase.GetDollarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DollarViewModel(
    val fetchDollarUseCase: GetDollarUseCase
): ViewModel() {

    sealed class DollarUIState {
        object Loading : DollarUIState()
        class Error(val message: String) : DollarUIState()
        class Success(val data: DollarModel) : DollarUIState()
    }

    init {
        getDollar()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()

    fun getDollar() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDollarUseCase.invoke().collect {
                    data -> _uiState.value = DollarUIState.Success(data) }
        }
    }
}