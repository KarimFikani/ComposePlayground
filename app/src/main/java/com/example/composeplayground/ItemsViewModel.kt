package com.example.composeplayground

import androidx.compose.runtime.Immutable
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

    private val _singleUiState by lazy {
        MutableStateFlow(SingleUiState())
    }
    val singleUiState: StateFlow<SingleUiState> get() = _singleUiState.asStateFlow()

    private val _multiUiState by lazy {
        MutableStateFlow<UiState<String>>(UiState.Idle)
    }
    val multiUiState: StateFlow<UiState<String>> get() = _multiUiState.asStateFlow()

    private val items = mutableListOf<Item>()

    fun fetchItemsAsSingleStateContinuously() {
        viewModelScope.launch {
            _singleUiState.value = SingleUiState(
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

                    _singleUiState.value = SingleUiState(
                        uiState = UiStateEnum.SUCCESS,
                        screenData = ScreenData(
                            items = items.toImmutableList()
                        )
                    )
                }
        }
    }

    fun fetchItemsAsSingleStateOneShot() {
        viewModelScope.launch {
            _singleUiState.value = SingleUiState(
                uiState = UiStateEnum.LOADING
            )

            itemsRepository.getItems()
                .onEach { println(it) }
                .collect { list ->
                    _singleUiState.value = SingleUiState(
                        uiState = UiStateEnum.SUCCESS,
                        screenData = ScreenData(
                            items = list.map { Item(value = it.toString(), isFavorite = false) }
                                .toImmutableList()
                        )
                    )
                }
        }
    }

    fun fetchItemsAsMultipleStates() {
        viewModelScope.launch {

        }
    }
}

enum class UiStateEnum {
    IDLE,
    LOADING,
    ERROR,
    SUCCESS
}

data class SingleUiState(
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

/**
 * [UiState] used for state flows defined in view models to send back the state to UI
 */
@Immutable
sealed interface UiState<out R> {

    object Idle : UiState<Nothing>

    /**
     * Loading state
     */
    object Loading : UiState<Nothing>

    /**
     * Error state containing an error message
     */
    data class Error(val errorMessage: String? = null) : UiState<Nothing>

    /**
     * Templated success type holding any data type
     */
    data class Success<T>(val data: T) : UiState<T>
}

val <T> UiState<T>.data: T?
    get() = (this as? UiState.Success)?.data

val <T> UiState<T>.errorMessage: String?
    get() = (this as? UiState.Error)?.errorMessage
