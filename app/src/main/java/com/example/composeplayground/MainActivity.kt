package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.collections.immutable.persistentListOf

/**
 * Simple demo to prove when recomposition happens and how to avoid it
 */
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ComposePlaygroundTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
////                    SimpleLazyVerticalGridRecomposable()
////                    SimpleLazyVerticalGridFix1()
//                    SimpleLazyVerticalGridFix2()
//                }
//            }
//        }
//    }
//}

/**
 * Advanced demo that contains more complex layout
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            StaticMainScreen()
            DynamicMainScreen()
//            LoginScreen()
        }
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicMainScreen(
    itemsViewModel: ItemsViewModel = ItemsViewModel()
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var isEditable by rememberSaveable { mutableStateOf(false) }

//    itemsViewModel.fetchItemsAsSingleStateOneShot()
    itemsViewModel.fetchItemsAsSingleStateContinuously()

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
            val screenUiState by itemsViewModel.singleUiState.collectAsState()

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
