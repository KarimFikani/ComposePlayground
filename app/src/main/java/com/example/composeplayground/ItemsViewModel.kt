package com.example.composeplayground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random

class ItemsViewModel : ViewModel() {

    private val itemsRepository = ItemsRepository()

    private val _uiState by lazy {
        MutableStateFlow(UiState())
    }
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    private val items = mutableListOf<Item>()

    fun fetchItemsContinuously() {
        viewModelScope.launch {
            _uiState.value = UiState(
                uiState = UiStateEnum.LOADING
            )

            itemsRepository.getItemsWithThrottle()
                .throttle(100)
                .collect {

                    items.add(
                        Item(
                            value = it.toString(),
                            isFavorite = false
                        )
                    )
                    items[0].isFavorite = Random.nextBoolean()

                    _uiState.value = UiState(
                        uiState = UiStateEnum.SUCCESS,
                        screenData = ScreenData(
                            items = items.toImmutableList()
                        )
                    )
                }
        }
    }

    fun fetchItemsOneShot() {
        viewModelScope.launch {
            _uiState.value = UiState(
                uiState = UiStateEnum.LOADING
            )

            itemsRepository.getItems()
                .onEach { println(it) }
                .collect { list ->
                    _uiState.value = UiState(
                        uiState = UiStateEnum.SUCCESS,
                        screenData = ScreenData(
                            items = list.map { Item(value = it.toString(), isFavorite = false) }
                                .toImmutableList()
                        )
                    )
                }
        }
    }
}

enum class UiStateEnum {
    IDLE,
    LOADING,
    ERROR,
    SUCCESS
}

data class UiState(
    val uiState: UiStateEnum = UiStateEnum.IDLE,
    val screenData: ScreenData? = null,
    val errorMessage: String? = null,
    val progress: Int? = null
)

data class ScreenData(
    val items: ImmutableList<Item> = persistentListOf(),
)

data class Item(
    val value: String,
    var isFavorite: Boolean
)
