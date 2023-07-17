package com.example.composeplayground.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import com.example.composeplayground.ItemsViewModel
import com.example.composeplayground.LazyVerticalGridFix
import com.example.composeplayground.UiStateEnum
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowOfItemsScreen(
    itemsViewModel: ItemsViewModel = ItemsViewModel()
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var isEditable by rememberSaveable { mutableStateOf(false) }

//    itemsViewModel.fetchItemsOneShot()
    itemsViewModel.fetchItemsContinuously()

    ComposePlaygroundTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    scrollBehavior = scrollBehavior,
                    onEditCLicked = { isEditable = !isEditable }
                )
            },
            snackbarHost = {},
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { paddingValues ->
            val screenUiState by itemsViewModel.uiState.collectAsState()

            when (screenUiState.uiState) {
                UiStateEnum.SUCCESS -> {
                    LazyVerticalGridFix(
                        items = screenUiState.screenData?.items ?: persistentListOf(),
                        paddingValues = paddingValues,
                        isEditable = isEditable
                    )
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onEditCLicked: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text = "Sample"
            )
        },
        actions = {
            IconButton(onClick = {
                onEditCLicked.invoke()
            }) {
                Text(
                    text = "Edit",
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticMainScreen() {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var isEditable by rememberSaveable { mutableStateOf(false) }

    ComposePlaygroundTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    scrollBehavior = scrollBehavior,
                    onEditCLicked = { isEditable = !isEditable }
                )
            },
            snackbarHost = {},
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
//                    LazyVerticalGridRecomposable(it)
            LazyVerticalGridFix(
                paddingValues = it,
                isEditable = isEditable
            )
        }
    }
}
