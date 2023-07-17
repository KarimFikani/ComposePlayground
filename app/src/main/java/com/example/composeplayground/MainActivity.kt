package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeplayground.screen.FlowOfItemsScreen
import com.example.composeplayground.screen.LazyColumnOfLazyRowsScreen
import com.example.composeplayground.screen.LoginScreen
import com.example.composeplayground.screen.SimpleLazyColumnRecomposableScreen
import com.example.composeplayground.screen.SimpleLazyRowRecomposableScreen
import com.example.composeplayground.screen.SimpleLazyVerticalGridBugFixScreen
import com.example.composeplayground.screen.SimpleLazyVerticalGridFix1Screen
import com.example.composeplayground.screen.SimpleLazyVerticalGridFix2Screen
import com.example.composeplayground.screen.SimpleLazyVerticalGridRecomposableScreen
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.collections.immutable.persistentListOf

/**
 * Simple demo to prove when recomposition happens and how to avoid it
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController
) {
    ComposePlaygroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    ScreenButton(
                        title = "Simple Lazy Vertical Grid Recomposable",
                        onButtonClicked = {
                            navController.navigate(Screen.SimpleLazyVerticalGridRecomposableScreen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Simple Lazy Vertical Grid Bug Fix",
                        onButtonClicked = {
                            navController.navigate(Screen.SimpleLazyVerticalGridBugFixScreen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Simple Lazy Vertical Grid Fix 1",
                        onButtonClicked = {
                            navController.navigate(Screen.SimpleLazyVerticalGridFix1Screen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Simple Lazy Vertical Grid Fix 2",
                        onButtonClicked = {
                            navController.navigate(Screen.SimpleLazyVerticalGridFix2Screen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Simple Lazy Column Recomposable",
                        onButtonClicked = {
                            navController.navigate(Screen.SimpleLazyColumnRecomposableScreen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Simple Lazy Row Recomposable",
                        onButtonClicked = {
                            navController.navigate(Screen.SimpleLazyRowRecomposableScreen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Lazy Column of Lazy Rows",
                        onButtonClicked = {
                            navController.navigate(Screen.LazyColumnOfLazyRowsScreen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Display a flow of items",
                        onButtonClicked = {
                            navController.navigate(Screen.FlowOfItemsScreen.route)
                        }
                    )

                    ButtonSpacer()

                    ScreenButton(
                        title = "Login screen",
                        onButtonClicked = {
                            navController.navigate(Screen.LoginScreen.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.SimpleLazyVerticalGridRecomposableScreen.route
        ) {
            SimpleLazyVerticalGridRecomposableScreen()
        }
        composable(
            route = Screen.SimpleLazyVerticalGridBugFixScreen.route
        ) {
            SimpleLazyVerticalGridBugFixScreen()
        }
        composable(
            route = Screen.SimpleLazyVerticalGridFix1Screen.route
        ) {
            SimpleLazyVerticalGridFix1Screen()
        }
        composable(
            route = Screen.SimpleLazyVerticalGridFix2Screen.route
        ) {
            SimpleLazyVerticalGridFix2Screen()
        }
        composable(
            route = Screen.SimpleLazyColumnRecomposableScreen.route
        ) {
            SimpleLazyColumnRecomposableScreen()
        }
        composable(
            route = Screen.SimpleLazyRowRecomposableScreen.route
        ) {
            SimpleLazyRowRecomposableScreen()
        }
        composable(
            route = Screen.LazyColumnOfLazyRowsScreen.route
        ) {
            LazyColumnOfLazyRowsScreen()
        }
        composable(
            route = Screen.FlowOfItemsScreen.route
        ) {
            FlowOfItemsScreen()
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen()
        }
    }
}

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "main_screen")
    object SimpleLazyVerticalGridRecomposableScreen :
        Screen(route = "simple_lazy_vertical_grid_recomposable_screen")
    object SimpleLazyVerticalGridBugFixScreen :
        Screen(route = "simple_lazy_vertical_grid_bug_fix_screen")
    object SimpleLazyVerticalGridFix1Screen :
        Screen(route = "simple_lazy_vertical_grid_fix_1_screen")
    object SimpleLazyVerticalGridFix2Screen :
        Screen(route = "simple_lazy_vertical_grid_fix_2_screen")
    object SimpleLazyColumnRecomposableScreen :
        Screen(route = "simple_lazy_column_recomposable_screen")
    object SimpleLazyRowRecomposableScreen :
        Screen(route = "simple_lazy_row_recomposable_screen")
    object LazyColumnOfLazyRowsScreen :
        Screen(route = "lazy_row_of_lazy_columns_screen")
    object FlowOfItemsScreen :
        Screen(route = "flow_of_items_screen")
    object LoginScreen :
        Screen(route = "login_screen")
}

@Composable
fun ScreenButton(
    title: String,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        TextButton(
            onClick = onButtonClicked,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(percent = 15),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 20.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = title,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun ButtonSpacer() {
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
}
