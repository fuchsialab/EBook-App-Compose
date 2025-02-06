package com.fuchsia.ebookapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuchsia.ebookapp.common.ResultState
import com.fuchsia.ebookapp.data.BookCategoryModels
import com.fuchsia.ebookapp.data.BookModels
import com.fuchsia.ebookapp.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val repo: Repo) : ViewModel() {

    private val _GetAllBookState: MutableStateFlow<GetAllBookState> = MutableStateFlow(GetAllBookState())
    val GetAllBookState: StateFlow<GetAllBookState> = _GetAllBookState.asStateFlow()

    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllBooks().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _GetAllBookState.value = GetAllBookState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _GetAllBookState.value = GetAllBookState(data = result.data)
                    }
                    is ResultState.Error -> {
                        _GetAllBookState.value = GetAllBookState(error = result.exception)
                    }
                }
            }
        }
    }

    private val _GetAllCategoryState: MutableStateFlow<GetAllCategoryState> = MutableStateFlow(GetAllCategoryState())
    val GetAllCategoryState: StateFlow<GetAllCategoryState> = _GetAllCategoryState.asStateFlow()

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCategory().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _GetAllCategoryState.value = GetAllCategoryState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _GetAllCategoryState.value = GetAllCategoryState(data = result.data)
                    }
                    is ResultState.Error -> {
                        _GetAllCategoryState.value = GetAllCategoryState(error = result.exception)
                    }
                }
            }
        }
    }

    private val _GetAllBooksByCategoryState: MutableStateFlow<GetAllBooksByCategoryState> = MutableStateFlow(GetAllBooksByCategoryState())
    val GetAllBooksByCategoryState: StateFlow<GetAllBooksByCategoryState> = _GetAllBooksByCategoryState.asStateFlow()

    fun getAllBookByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllBooksByCategory(category).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _GetAllBooksByCategoryState.value = GetAllBooksByCategoryState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _GetAllBooksByCategoryState.value = GetAllBooksByCategoryState(data = result.data)
                    }
                    is ResultState.Error -> {
                        _GetAllBooksByCategoryState.value = GetAllBooksByCategoryState(error = result.exception)
                    }
                }
            }
        }
    }
}

data class GetAllBookState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<BookModels>? = emptyList()
)

data class GetAllCategoryState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<BookCategoryModels>? = emptyList()
)

data class GetAllBooksByCategoryState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<BookModels>? = emptyList()
)
