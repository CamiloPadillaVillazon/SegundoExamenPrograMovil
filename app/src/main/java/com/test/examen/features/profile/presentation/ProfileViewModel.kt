package com.test.examen.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.examen.features.profile.domain.model.ProfileModel
import com.test.examen.features.profile.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    sealed class UIState {
        data object Loading : UIState()
        data class Success(val data: ProfileModel) : UIState()
        data class Error(val message: String) : UIState()
    }

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getUserUseCase.invoke()
                _uiState.value = UIState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "error")
            }
        }
    }
}
